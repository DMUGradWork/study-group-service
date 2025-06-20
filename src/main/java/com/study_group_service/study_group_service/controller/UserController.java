package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 모든 유저 조회
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        userService.getUsers();
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // 특정 유저 조회(id)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUser(@PathVariable Long id) {
        userService.getUserById(id);
        return ResponseEntity.ok().build();
    }

    // 특정 유저 조회(email)
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findUserByEmail(@PathVariable String email) {
        userService.getUserByEmail(email);
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    // 회원 생성
    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        userService.setUsers(userDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 관리자로 변경
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUserRoleToAdmin(@PathVariable String email) {
        userService.updateUserRoleToAdmin(email);
        return ResponseEntity.noContent().build();
    }
}
