package com.study_group_service.study_group_service.service.users;

import com.study_group_service.study_group_service.dto.users.UsersDTO;

import java.util.List;

public interface UsersService {
    List<UsersDTO> getUsers();
    UsersDTO getUserById(Long id);
    UsersDTO getUserByEmail(String email);
    UsersDTO setUsers(UsersDTO usersDTO);
    void deleteUser(Long id);
    void updateUserRoleToAdmin(String email);
}
