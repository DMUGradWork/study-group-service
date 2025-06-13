package com.study_group_service.study_group_service.service.user;

import com.study_group_service.study_group_service.dto.user.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO setUsers(UserDTO userDTO);
    void deleteUser(Long id);
    void updateUserRoleToAdmin(String email);
}
