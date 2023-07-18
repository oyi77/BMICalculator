package com.mybmiapp.dao;

import com.mybmiapp.models.User;

import java.util.List;

public interface UserDao {
    User getUserByEmail(String email);
    List<User> getAllUsers();
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}
