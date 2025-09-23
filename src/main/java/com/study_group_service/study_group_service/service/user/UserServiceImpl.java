package com.study_group_service.study_group_service.service.user;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.entity.user.Admin;
import com.study_group_service.study_group_service.event.user.UserEvent;
import com.study_group_service.study_group_service.event.user.UserEventPublisher;
import com.study_group_service.study_group_service.entity.user.User;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.exception.user.AlreadyEmailExistsException;
import com.study_group_service.study_group_service.exception.user.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.user.AdminMapper;
import com.study_group_service.study_group_service.mapper.user.UserMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.user.AdminJpaRepository;
import com.study_group_service.study_group_service.repository.user.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final AdminJpaRepository adminJpaRepository;
    private final ErrorMessage errorMessage;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final UserEventPublisher userEventPublisher;

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

        UserDTO result = userMapper.toDto(savedUser);

        userEventPublisher.publish(UserEvent.builder()
                .type(UserEvent.Type.CREATED)
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole().name())
                .build());

        return result;
    }

    // 특정 회원 삭제
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        userJpaRepository.delete(user);

        userEventPublisher.publish(UserEvent.builder()
                .type(UserEvent.Type.DELETED)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build());
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

        userEventPublisher.publish(UserEvent.builder()
                .type(UserEvent.Type.UPDATED)
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build());
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
    // 임시 (회의 후 로직 생성 예정)
    public void joinRoom(Long userId) {
    }

    @Override
    @Transactional
    // 임시 (회의 후 로직 생성 예정)
    public void leaveRoom(Long userId) {
    }


}
