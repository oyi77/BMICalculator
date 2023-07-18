package com.mybmiapp.dao;

import com.mybmiapp.models.User;
import com.mybmiapp.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private Connection connection;

    // Constructor to initialize the connection (you may use a connection pool in production)
    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        String query = "INSERT INTO users (email, password, gender) VALUES (?, ?, ?)"; // Add gender to the query
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getGender()); // Set the gender in the query
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @Override
    public List<User> getBlockedUsersByUserId(int userId) {
        // Implementation for getting blocked users (if needed)
        // You can handle blocked users in BlockedUserDaoImpl
        return null;
    }

    @Override
    public boolean blockUser(int userId, int blockedUserId) {
        String query = "INSERT INTO blocked_users (user_id, blocked_user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, blockedUserId);
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return false;
        }
    }

    @Override
    public boolean unblockUser(int userId, int blockedUserId) {
        String query = "DELETE FROM blocked_users WHERE user_id = ? AND blocked_user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, blockedUserId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return false;
        }
    }

    @Override
    public User getUserById(int userId) {
        User user = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");

                user = new User(userId, email, password, gender);
                // Set other user properties if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers(int currentUserId) {
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();

        String query = "SELECT * FROM users WHERE id != ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, currentUserId);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String gender = resultSet.getString("gender");
                // Retrieve other user attributes from the database

                User user = new User(id, email, password, gender);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Helper method to extract user details from the ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(0, "", "", "");
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        // Set other user properties if needed
        return user;
    }

}
