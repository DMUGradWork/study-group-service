package com.study_group_service.study_group_service;

import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.enums.Role;
import com.study_group_service.study_group_service.exception.users.AlreadyEmailExistsException;
import com.study_group_service.study_group_service.exception.users.UserNotFoundException;
import com.study_group_service.study_group_service.repository.users.AdminJpaRepository;
import com.study_group_service.study_group_service.repository.users.UsersJpaRepository;
import com.study_group_service.study_group_service.service.users.UsersService;
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
public class UsersTest {

    @Autowired
    private UsersJpaRepository usersJpaRepository;

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    @Autowired
    private UsersService usersService;

    private UsersDTO createUserDTO(String email, Role role) {
        return UsersDTO.builder()
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
        usersService.setUsers(createUserDTO("user1@test.com", Role.USER));
        usersService.setUsers(createUserDTO("user2@test.com", Role.USER));

        // when
        List<UsersDTO> users = usersService.getUsers();

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
        UsersDTO userDto = createUserDTO("normal@test.com", Role.USER);

        // when
        UsersDTO savedUser = usersService.setUsers(userDto);

        // then
        assertThat(savedUser.getEmail()).isEqualTo("normal@test.com");
        assertThat(usersJpaRepository.findAll()).hasSize(1);
        assertThat(adminJpaRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("관리자_회원_생성")
    @Transactional
    void shouldCreateAdminUser() {
        // given
        UsersDTO adminDto = createUserDTO("admin@test.com", Role.ADMIN);

        // when
        UsersDTO savedAdmin = usersService.setUsers(adminDto);

        // then
        assertThat(savedAdmin.getRole()).isEqualTo(Role.ADMIN);
        assertThat(adminJpaRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("이메일로_회원_조회_성공")
    @Transactional
    void shouldFindUserByEmail() {
        // given
        UsersDTO userDto = createUserDTO("find@test.com", Role.USER);
        usersService.setUsers(userDto);

        // when
        UsersDTO foundUser = usersService.getUserByEmail("find@test.com");

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
        assertThatThrownBy(() -> usersService.getUserByEmail(nonexistentEmail))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("회원_삭제_후_조회_시_예외_발생")
    @Transactional
    void shouldDeleteUserById() {
        // given
        UsersDTO savedUser = usersService.setUsers(createUserDTO("delete@test.com", Role.USER));

        // when
        usersService.deleteUser(savedUser.getId());

        // then
        assertThatThrownBy(() -> usersService.getUserById(savedUser.getId()))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지_않는_ID로_회원_삭제_시_예외_발생")
    @Transactional
    void shouldThrowWhenDeletingNonexistentUser() {
        // given
        Long id = 999L;

        // when & then
        assertThatThrownBy(() -> usersService.deleteUser(id))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("중복_이메일_회원_생성_시_예외_발생")
    @Transactional
    void shouldThrowWhenDuplicateEmail() {
        // given
        UsersDTO user1 = createUserDTO("duplicate2@test.com", Role.USER);
        usersService.setUsers(user1);

        // when & then
        UsersDTO user2 = createUserDTO("duplicate2@test.com", Role.USER);
        assertThatThrownBy(() -> usersService.setUsers(user2))
                .isInstanceOf(AlreadyEmailExistsException.class);
    }


}
