package com.study_group_service.study_group_service.service.user;

import com.study_group_service.study_group_service.dto.user.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByUuid(UUID uuid);
    UserDTO getUserByEmail(String email);
    UserDTO setUsers(UserDTO userDTO);
    void deleteUser(Long id);
    void deleteUserByUuid(UUID uuid);
    void updateUserRoleToAdmin(String email);
    void checkAttendance(Long userId);
    void checkAttendanceByUuid(UUID userUuid);
    void joinRoom(Long userId);
    void joinRoomByUuid(UUID userUuid);
    void leaveRoom(Long userId);
    void leaveRoomByUuid(UUID userUuid);
}
