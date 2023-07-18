package com.mybmiapp.servlets;

import com.mybmiapp.dao.UserDao;
import com.mybmiapp.dao.UserDaoImpl;
import com.mybmiapp.models.User;
import com.mybmiapp.util.DatabaseConnection;
import com.mybmiapp.util.PasswordHasher;
import com.mybmiapp.util.UserSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDao userDao;
    private final Connection dm;

    public LoginServlet() throws SQLException {
        this.dm = DatabaseConnection.getConnection();
        userDao = new UserDaoImpl(dm); // Replace with your preferred DI approach

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=Please fill in all fields.");
            return;
        }

        User user = userDao.getUserByEmail(email);

        if (user == null || !PasswordHasher.verifyPassword(password, user.getPassword())) {
            response.sendRedirect("login.jsp?error=Invalid email or password.");
            return;
        }
        if (user != null) {
            HttpSession session = request.getSession();
            UserSessionUtil.setCurrentUser(session, user);
            response.sendRedirect("calculator");// Redirect to the user's dashboard or any other page after login

            // Set the user in the session for future authentication
            request.getSession().setAttribute("user", user);
        } else {
            response.sendRedirect("login?error=Invalid credentials"); // Redirect to login page with an error message
        }

    }
}
