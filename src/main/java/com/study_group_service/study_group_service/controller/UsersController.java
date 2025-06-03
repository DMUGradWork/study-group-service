package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    // 모든 유저 조회
    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAllUsers() {
        usersService.getUsers();
        return ResponseEntity.ok().body(usersService.getUsers());
    }

    // 특정 유저 조회(id)
    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> findUser(@PathVariable Long id) {
        usersService.getUserById(id);
        return ResponseEntity.ok().build();
    }

    // 특정 유저 조회(email)
    @GetMapping("/email/{email}")
    public ResponseEntity<UsersDTO> findUserByEmail(@PathVariable String email) {
        usersService.getUserByEmail(email);
        return ResponseEntity.ok().body(usersService.getUserByEmail(email));
    }

    // 회원 생성
    @PostMapping
    public ResponseEntity<UsersDTO> saveUser(@RequestBody UsersDTO usersDTO) {
        usersService.setUsers(usersDTO);
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 관리자로 변경
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUserRoleToAdmin(@PathVariable String email) {
        usersService.updateUserRoleToAdmin(email);
        return ResponseEntity.noContent().build();
    }
}
