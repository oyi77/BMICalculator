package com.mybmiapp.dao;

import com.mybmiapp.models.Calculation;
import com.mybmiapp.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalculationDaoImpl implements CalculationDao {

    private Connection connection;

    // Constructor to initialize the connection (you may use a connection pool in production)
    public CalculationDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addCalculation(Calculation calculation) {
        String query = "INSERT INTO calculations (user_id, gender, height, weight, bmi, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, calculation.getUserId());
            pstmt.setString(2, calculation.getGender());
            pstmt.setDouble(3, calculation.getHeight());
            pstmt.setDouble(4, calculation.getWeight());
            pstmt.setDouble(5, calculation.getBmi());
            pstmt.setString(6, calculation.getCategory());
            // Set other calculation attributes in the query if needed

            // Execute the insert query
            int rowsInserted = pstmt.executeUpdate();

            // Return true if the operation is successful (1 row inserted), false otherwise
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // An error occurred, return false
        }
    }

    @Override
    public List<Calculation> getCalculationsByUserId(int userId) {
        ResultSet resultSet = null;
        List<Calculation> calculations = new ArrayList<>();

        String query = "SELECT * FROM calculations WHERE user_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String gender = resultSet.getString("gender");
                double height = resultSet.getDouble("height");
                double weight = resultSet.getDouble("weight");
                double bmi = resultSet.getDouble("bmi");
                String category = resultSet.getString("category");
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime(); // Retrieve the timestamp from the database
               // Retrieve other calculation attributes from the database
                Calculation calculation = new Calculation(id, userId, gender, height, weight, bmi, category,timestamp);
                calculation.setTimestamp(timestamp);
                calculations.add(calculation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return calculations;
    }

    // Implement other calculation-related methods as needed
}
