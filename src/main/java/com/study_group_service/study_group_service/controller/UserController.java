package com.study_group_service.study_group_service.controller;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    // 특정 유저 조회(uuid)
    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<UserDTO> findUserByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(userService.getUserByUuid(uuid));
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
        return ResponseEntity.ok().body(userService.setUsers(userDTO));
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 회원 삭제 (uuid)
    @DeleteMapping("/uuid/{uuid}")
    public ResponseEntity<Void> deleteUserByUuid(@PathVariable UUID uuid) {
        userService.deleteUserByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

    // 관리자로 변경
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUserRoleToAdmin(@PathVariable String email) {
        userService.updateUserRoleToAdmin(email);
        return ResponseEntity.noContent().build();
    }

    // 출석 체크
    @PostMapping("/{id}/attendance")
    public ResponseEntity<Void> checkAttendance(@PathVariable Long id) {
        userService.checkAttendance(id);
        return ResponseEntity.ok().build();
    }

    // 출석 체크 (uuid)
    @PostMapping("/uuid/{uuid}/attendance")
    public ResponseEntity<Void> checkAttendanceByUuid(@PathVariable UUID uuid) {
        userService.checkAttendanceByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    // 방 참여
    @PostMapping("/{id}/join-room")
    public ResponseEntity<Void> joinRoom(@PathVariable Long id) {
        userService.joinRoom(id);
        return ResponseEntity.ok().build();
    }

    // 방 참여 (uuid)
    @PostMapping("/uuid/{uuid}/join-room")
    public ResponseEntity<Void> joinRoomByUuid(@PathVariable UUID uuid) {
        userService.joinRoomByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    // 방 퇴장
    @PostMapping("/{id}/leave-room")
    public ResponseEntity<Void> leaveRoom(@PathVariable Long id) {
        userService.leaveRoom(id);
        return ResponseEntity.ok().build();
    }

    // 방 퇴장 (uuid)
    @PostMapping("/uuid/{uuid}/leave-room")
    public ResponseEntity<Void> leaveRoomByUuid(@PathVariable UUID uuid) {
        userService.leaveRoomByUuid(uuid);
        return ResponseEntity.ok().build();
    }

    // 로그인 (이메일+비밀번호)
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        UserDTO user = userService.getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(user);
    }
}
