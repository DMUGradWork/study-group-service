package com.study_group_service.study_group_service;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.exception.user.AlreadyEmailExistsException;
import com.study_group_service.study_group_service.exception.user.UserNotFoundException;
import com.study_group_service.study_group_service.repository.user.AdminJpaRepository;
import com.study_group_service.study_group_service.repository.user.UserJpaRepository;
import com.study_group_service.study_group_service.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    @Autowired
    private UserService userService;

    private UserDTO createUserDTO(String email, Role role) {
        return UserDTO.builder()
                .email(email)
                .password("testPassword")
                .name("testName")
                .phone("010-1234-5678")
                .role(role)
                .created_at(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("회원_전체_조회")
    @Transactional
    void shouldReturnAllUsers() {
        // given
        userService.setUsers(createUserDTO("user1@test.com", Role.USER));
        userService.setUsers(createUserDTO("user2@test.com", Role.USER));

        // when
        List<UserDTO> users = userService.getUsers();

        // then
        assertThat(users).hasSize(2);
        assertThat(users).extracting("email")
                .containsExactlyInAnyOrder("user1@test.com", "user2@test.com");
    }

    @Test
    @DisplayName("일반_사용자_회원_생성")
    @Transactional
    void shouldCreateNormalUser() {
        // given
        UserDTO userDto = createUserDTO("normal@test.com", Role.USER);

        // when
        UserDTO savedUser = userService.setUsers(userDto);

        // then
        assertThat(savedUser.getEmail()).isEqualTo("normal@test.com");
        assertThat(userJpaRepository.findAll()).hasSize(1);
        assertThat(adminJpaRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("관리자_회원_생성")
    @Transactional
    void shouldCreateAdminUser() {
        // given
        UserDTO adminDto = createUserDTO("admin@test.com", Role.ADMIN);

        // when
        UserDTO savedAdmin = userService.setUsers(adminDto);

        // then
        assertThat(savedAdmin.getRole()).isEqualTo(Role.ADMIN);
        assertThat(adminJpaRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("이메일로_회원_조회_성공")
    @Transactional
    void shouldFindUserByEmail() {
        // given
        UserDTO userDto = createUserDTO("find@test.com", Role.USER);
        userService.setUsers(userDto);

        // when
        UserDTO foundUser = userService.getUserByEmail("find@test.com");

        // then
        assertThat(foundUser.getEmail()).isEqualTo("find@test.com");
    }

    @Test
    @DisplayName("존재하지_않는_이메일_조회_시_예외_발생")
    @Transactional
    void shouldThrowWhenEmailNotFound() {
        // given
        String nonexistentEmail = "notfound@test.com";

        // when & then
        assertThatThrownBy(() -> userService.getUserByEmail(nonexistentEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("회원_삭제_후_조회_시_예외_발생")
    @Transactional
    void shouldDeleteUserById() {
        // given
        UserDTO savedUser = userService.setUsers(createUserDTO("delete@test.com", Role.USER));

        // when
        userService.deleteUser(savedUser.getId());

        // then
        assertThatThrownBy(() -> userService.getUserById(savedUser.getId()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지_않는_ID로_회원_삭제_시_예외_발생")
    @Transactional
    void shouldThrowWhenDeletingNonexistentUser() {
        // given
        Long id = 999L;

        // when & then
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("중복_이메일_회원_생성_시_예외_발생")
    @Transactional
    void shouldThrowWhenDuplicateEmail() {
        // given
        UserDTO user1 = createUserDTO("duplicate2@test.com", Role.USER);
        userService.setUsers(user1);

        // when & then
        UserDTO user2 = createUserDTO("duplicate2@test.com", Role.USER);
        assertThatThrownBy(() -> userService.setUsers(user2))
                .isInstanceOf(AlreadyEmailExistsException.class);
    }


}
