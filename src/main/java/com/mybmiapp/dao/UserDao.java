package com.mybmiapp.dao;

import com.mybmiapp.models.User;
import java.util.List;

public interface UserDao {
    List<User> getAllUsers(int id);
    User getUserByEmail(String email);
    void addUser(User user);
    List<User> getBlockedUsersByUserId(int userId);
    boolean blockUser(int userId, int blockedUserId);
    boolean unblockUser(int userId, int blockedUserId);
    User getUserById(int blockedUserId);
}
