package com.study_group_service.study_group_service.service.user;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.entity.user.Admin;
import com.study_group_service.study_group_service.entity.user.User;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.event.user.UserProfileUpdated;
import com.study_group_service.study_group_service.event.user.UserRegistered;
import com.study_group_service.study_group_service.exception.user.AlreadyEmailExistsException;
import com.study_group_service.study_group_service.exception.user.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.user.AdminMapper;
import com.study_group_service.study_group_service.mapper.user.UserMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.user.AdminJpaRepository;
import com.study_group_service.study_group_service.repository.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final AdminJpaRepository adminJpaRepository;
    private final ErrorMessage errorMessage;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final ApplicationEventPublisher eventPublisher;

    // 모든 회원 조회
    @Override
    @Transactional
    public List<UserDTO> getUsers() {
        List<User> users = userJpaRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException(errorMessage.showNoUserMessage());
        }

        return userMapper.toDto(users);
    }

    // 특정 회원 조회
    @Override
    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        return userMapper.toDto(user);
    }

    // 특정 회원 조회(이메일)
    @Override
    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));

        return userMapper.toDto(user);
    }

    // 특정 회원 조회(UUID)
    @Override
    @Transactional
    public UserDTO getUserByUuid(UUID uuid) {
        User user = userJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        return userMapper.toDto(user);
    }

    // 회원 저장
    @Override
    @Transactional
    public UserDTO setUsers(UserDTO userDTO) {
        if (userJpaRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlreadyEmailExistsException(errorMessage.showAlreadyUseEmailMessage());
        }

        User users = userMapper.toEntity(userDTO);

        User savedUser = userJpaRepository.save(users);

        Optional.of(savedUser)
                .filter(user -> user.getRole() == Role.ADMIN)
                .map(adminUser -> Admin.builder()
                        .user(adminUser)
                        .email(adminUser.getEmail())
                        .password(adminUser.getPassword())
                        .phone(adminUser.getPhone())
                        .build())
                .ifPresent(adminJpaRepository::save);

        // 사용자 등록 이벤트 발행
        UserRegistered userRegisteredEvent = new UserRegistered(
                savedUser.getUuid(),
                LocalDateTime.now(),
                savedUser.getNickname()
        );
        eventPublisher.publishEvent(userRegisteredEvent);

        return userMapper.toDto(savedUser);
    }

    // 사용자 프로필 업데이트
    @Override
    @Transactional
    public UserDTO updateUserProfile(UUID userUuid, UserDTO userDTO) {
        User existingUser = userJpaRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        String oldNickname = existingUser.getNickname();
        
        // 프로필 정보 업데이트
        existingUser.setNickname(userDTO.getNickname());
        existingUser.setPhone(userDTO.getPhone());
        
        User updatedUser = userJpaRepository.save(existingUser);

        // 사용자 프로필 업데이트 이벤트 발행
        UserProfileUpdated profileUpdatedEvent = new UserProfileUpdated(
                updatedUser.getUuid(),
                oldNickname,
                updatedUser.getNickname(),
                "nickname,phone", // 업데이트된 필드들
                LocalDateTime.now()
        );
        eventPublisher.publishEvent(profileUpdatedEvent);

        return userMapper.toDto(updatedUser);
    }

    // 특정 회원 삭제
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        userJpaRepository.delete(user);
    }

    // 특정 회원 삭제(UUID)
    @Override
    @Transactional
    public void deleteUserByUuid(UUID uuid) {
        User user = userJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        userJpaRepository.delete(user);
    }

    // 관리자로 변경
    @Override
    @Transactional
    public void updateUserRoleToAdmin(String email) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));

        // 엔티티에 정의한 메서드 사용
        user.promoteToAdmin();
        userJpaRepository.save(user);

        Admin admin = adminJpaRepository.findByEmail(email)
                .map(existingAdmin -> {
                    adminMapper.updateEntity(existingAdmin, user);
                    return existingAdmin;
                })
                .orElseGet(() -> adminMapper.toEntity(user));

        adminJpaRepository.save(admin);
    }

    @Override
    @Transactional
    public void checkAttendance(Long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        user.checkAttendance(java.time.LocalDate.now());
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    public void checkAttendanceByUuid(UUID userUuid) {
        User user = userJpaRepository.findByUuid(userUuid)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        user.checkAttendance(java.time.LocalDate.now());
        userJpaRepository.save(user);
    }

    @Override
    @Transactional
    // 임시 (회의 후 로직 생성 예정)
    public void joinRoom(Long userId) {
    }

    @Override
    @Transactional
    // 임시 (회의 후 로직 생성 예정)
    public void joinRoomByUuid(UUID userUuid) {
    }

    @Override
    @Transactional
    // 임시 (회의 후 로직 생성 예정)
    public void leaveRoom(Long userId) {
    }

    @Override
    @Transactional
    // 임시 (회의 후 로직 생성 예정)
    public void leaveRoomByUuid(UUID userUuid) {
    }


}
