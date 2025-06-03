package com.study_group_service.study_group_service.service.users;

import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.entity.user.Admin;
import com.study_group_service.study_group_service.entity.user.Users;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.exception.users.AlreadyEmailExistsException;
import com.study_group_service.study_group_service.exception.users.UserNotFoundException;
import com.study_group_service.study_group_service.mapper.users.AdminMapper;
import com.study_group_service.study_group_service.mapper.users.UsersMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.message.SuccessMessage;
import com.study_group_service.study_group_service.repository.users.AdminJpaRepository;
import com.study_group_service.study_group_service.repository.users.UsersJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersJpaRepository usersJpaRepository;
    private final AdminJpaRepository adminJpaRepository;
    private final ErrorMessage errorMessage;
    private final UsersMapper usersMapper;
    private final AdminMapper adminMapper;

    // 모든 회원 조회
    @Override
    @Transactional
    public List<UsersDTO> getUsers() {
        List<Users> users = usersJpaRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException(errorMessage.showNoUserMessage());
        }

        return usersMapper.toDto(users);
    }

    // 특정 회원 조회
    @Override
    @Transactional
    public UsersDTO getUserById(Long id) {
        Users user = usersJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));
        return usersMapper.toDto(user);
    }

    // 특정 회원 조회(이메일)
    @Override
    @Transactional
    public UsersDTO getUserByEmail(String email) {
        Users user = usersJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));

        return usersMapper.toDto(user);
    }

    // 회원 저장
    @Override
    @Transactional
    public UsersDTO setUsers(UsersDTO usersDTO) {
        if (usersJpaRepository.existsByEmail(usersDTO.getEmail())) {
            throw new AlreadyEmailExistsException(errorMessage.showAlreadyUseEmailMessage());
        }

        Users users = usersMapper.toEntity(usersDTO);
        users.assignDefaultRoleIfNull();
        users.assignCreatedAtNow();

        Users savedUser = usersJpaRepository.save(users);

        Optional.of(savedUser)
                .filter(user -> user.getRole() == Role.ADMIN)
                .map(adminUser -> Admin.builder()
                        .user(adminUser)
                        .email(adminUser.getEmail())
                        .password(adminUser.getPassword())
                        .phone(adminUser.getPhone())
                        .build())
                .ifPresent(adminJpaRepository::save);

        return usersMapper.toDto(savedUser);
    }

    // 특정 회원 삭제
    @Override
    @Transactional
    public void deleteUser(Long id) {
        Users users = usersJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserMessage()));

        usersJpaRepository.delete(users);
    }

    // 관리자로 변경
    @Override
    @Transactional
    public void updateUserRoleToAdmin(String email) {
        Users user = usersJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(errorMessage.showNoUserEmailMessage()));

        // 엔티티에 정의한 메서드 사용
        user.promoteToAdmin();
        usersJpaRepository.save(user);

        Admin admin = adminJpaRepository.findByEmail(email)
                .map(existingAdmin -> {
                    adminMapper.updateEntity(existingAdmin, user);
                    return existingAdmin;
                })
                .orElseGet(() -> adminMapper.toEntity(user));

        adminJpaRepository.save(admin);
    }


}
