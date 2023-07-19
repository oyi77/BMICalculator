package com.mybmiapp.dao;

import com.mybmiapp.models.BlockedUser;
import com.mybmiapp.models.User;

import java.util.List;

public interface BlockedUserDao {
    void blockUser(BlockedUser blockedUser);
    void unblockUser(BlockedUser blockedUser);
    List<BlockedUser> getBlockedUsersByUserId(int userId);

    public boolean isBlocked(int id, int receiverId);

    public List<User> getBlockedUsers(int id);
}
