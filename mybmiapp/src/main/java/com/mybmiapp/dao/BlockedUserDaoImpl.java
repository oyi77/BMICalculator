package com.mybmiapp.dao;

import com.mybmiapp.models.BlockedUser;
import com.mybmiapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlockedUserDaoImpl implements BlockedUserDao {

    private Connection connection;

    // Constructor to initialize the connection (you may use a connection pool in production)
    public BlockedUserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void blockUser(BlockedUser blockedUser) {
        String query = "INSERT INTO blocked_users (user_id, blocked_user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, blockedUser.getUserId());
            pstmt.setInt(2, blockedUser.getBlockedUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public void unblockUser(BlockedUser blockedUser) {
        String query = "DELETE FROM blocked_users WHERE user_id = ? AND blocked_user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, blockedUser.getUserId());
            pstmt.setInt(2, blockedUser.getBlockedUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public List<BlockedUser> getBlockedUsersByUserId(int userId) {
        List<BlockedUser> blockedUsers = new ArrayList<>();
        String query = "SELECT * FROM blocked_users WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BlockedUser blockedUser = new BlockedUser(0,0,0);
                    blockedUser.setId(rs.getInt("id"));
                    blockedUser.setUserId(rs.getInt("user_id"));
                    blockedUser.setBlockedUserId(rs.getInt("blocked_user_id"));
                    blockedUsers.add(blockedUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return blockedUsers;
    }

    @Override
public boolean isBlocked(int userId, int blockedUserId) {
    String query = "SELECT COUNT(*) FROM blocked_users WHERE user_id = ? AND blocked_user_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, userId);
        pstmt.setInt(2, blockedUserId);
        try (ResultSet resultSet = pstmt.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
    return false;
}

@Override
public List<User> getBlockedUsers(int userId) {
    List<User> blockedUsers = new ArrayList<>();
    String query = "SELECT u.id, u.email, u.password, u.gender FROM users u " +
                   "INNER JOIN blocked_users b ON u.id = b.blocked_user_id " +
                   "WHERE b.user_id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
        pstmt.setInt(1, userId);
        try (ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                User blockedUser = new User(id, email, password, gender);
                blockedUsers.add(blockedUser);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
    return blockedUsers;
}

}
