<%-- 
    Document   : login.jsp
    Created on : 17 Jul 2023, 11.10.44
    Author     : vpmac2
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - BMI Calculator</title>
</head>
<body>
    <h1>Login</h1>
    <%-- Display an error message if there's any --%>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form action="login" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <input type="submit" value="Login">
    </form>

    <p>Don't have an account? <a href="register">Register</a></p>
</body>
</html>

