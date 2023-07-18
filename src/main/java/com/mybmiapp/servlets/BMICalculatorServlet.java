package com.mybmiapp.servlets;

import com.mybmiapp.dao.CalculationDao;
import com.mybmiapp.dao.CalculationDaoImpl;
import com.mybmiapp.dao.UserDao;
import com.mybmiapp.dao.UserDaoImpl;
import com.mybmiapp.models.Calculation;
import com.mybmiapp.models.User;
import com.mybmiapp.services.BMICalculator;
import com.mybmiapp.util.DatabaseConnection;
import com.mybmiapp.util.UserSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime; // Import LocalDateTime
import javax.servlet.RequestDispatcher;

@WebServlet("/calculator")
public class BMICalculatorServlet extends HttpServlet {

    private final CalculationDao calculationDao;
    private final UserDao userDao;
    private final Connection dm;

    public BMICalculatorServlet() throws SQLException {
        this.dm = DatabaseConnection.getConnection();
        calculationDao = new CalculationDaoImpl(dm);
        userDao = new UserDaoImpl(dm);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("calculator.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        double height = Double.parseDouble(request.getParameter("height"));
        double weight = Double.parseDouble(request.getParameter("weight"));

        User currentUser = UserSessionUtil.getCurrentUser(request.getSession());

        if (currentUser == null) {
            // Redirect to the login page if the user is not authenticated
            response.sendRedirect("login.jsp");
            return;
        } else {
            currentUser = userDao.getUserById(currentUser.getId());
            
            double bmi = BMICalculator.calculateBMI(height, weight);
            String category = BMICalculator.determineBMICategory(bmi);
            String weightStatus = BMICalculator.determineWeightStatus(category);
            String idealWeightRange = BMICalculator.calculateIdealWeightRange(height);

            // Get the current timestamp
            LocalDateTime timestamp = LocalDateTime.now();

            Calculation calculation = new Calculation(
                    0, // The ID will be automatically generated by the database
                    currentUser.getId(),
                    currentUser.getGender(),
                    height,
                    weight,
                    bmi,
                    category,
                    timestamp // Add the timestamp to the Calculation object
            );
            calculationDao.addCalculation(calculation);

            // Set the BMI calculation results as attributes in the request
            request.setAttribute("bmiValue", bmi);
            request.setAttribute("bmiCategory", category);
            request.setAttribute("weightStatus", weightStatus);
            request.setAttribute("idealWeightRange", idealWeightRange);

            // Forward to result.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
            dispatcher.forward(request, response);
        }
    }
}