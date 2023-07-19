package com.mybmiapp.servlets;

import com.mybmiapp.dao.UserDao;
import com.mybmiapp.dao.UserDaoImpl;
import com.mybmiapp.models.User;
import com.mybmiapp.util.DatabaseConnection;
import com.mybmiapp.util.PasswordHasher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDao userDao;
    private final Connection dm;

    public RegisterServlet() throws SQLException {
        this.dm = DatabaseConnection.getConnection();
        userDao = new UserDaoImpl(dm); // Replace with your preferred DI approach
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");

        if (email == null || email.isEmpty() || password == null || password.isEmpty() || gender == null || gender.isEmpty()) {
            response.sendRedirect("register.jsp?error=Please fill in all fields.");
            return;
        }

        // Check if the email is already registered
        if (userDao.getUserByEmail(email) != null) {
            response.sendRedirect("register.jsp?error=Email is already registered.");
            return;
        }

        // Hash the password before storing it in the database
        String hashedPassword = PasswordHasher.hashPassword(password);

        User user = new User(0, email, hashedPassword, gender); // Include the gender in the User object
        userDao.addUser(user);

        response.sendRedirect("login.jsp");
    }
}
