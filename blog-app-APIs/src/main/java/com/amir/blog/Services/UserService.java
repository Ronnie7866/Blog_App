package com.amir.blog.Services;

import com.amir.blog.Entities.User;
import com.amir.blog.Payloads.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, Integer userID);

    UserDTO getUserById(Integer id);

    List<UserDTO> getAllUsers();

    void deleteUser(Integer userId);
}
