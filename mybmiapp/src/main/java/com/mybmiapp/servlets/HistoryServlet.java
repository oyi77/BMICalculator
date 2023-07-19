package com.mybmiapp.servlets;

import com.mybmiapp.dao.CalculationDao;
import com.mybmiapp.dao.CalculationDaoImpl;
import com.mybmiapp.dao.UserDao;
import com.mybmiapp.dao.UserDaoImpl;
import com.mybmiapp.models.Calculation;
import com.mybmiapp.models.User;
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
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private final CalculationDao calculationDao;
    private final UserDao userDao;
    private final Connection dm;

    public HistoryServlet() throws SQLException {
        this.dm = DatabaseConnection.getConnection();
        calculationDao = new CalculationDaoImpl(dm);
        userDao = new UserDaoImpl(dm);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assume user information is available in the session (login required)
        User currentUser = UserSessionUtil.getCurrentUser(request.getSession());

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        } else {

            currentUser = userDao.getUserById(currentUser.getId());
            List<Calculation> calculations = calculationDao.getCalculationsByUserId(currentUser.getId());
            
            request.setAttribute("calculations", calculations);
            request.getRequestDispatcher("history.jsp").forward(request, response);
        }
    }
}
