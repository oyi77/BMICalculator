<%-- 
    Document   : calculator.jsp
    Created on : 17 Jul 2023, 11.12.31
    Author     : vpmac2
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BMI Calculator</title>
</head>
<body>
    <h1>BMI Calculator</h1>

    <form action="calculator" method="post">
        <label for="height">Height (cm):</label>
        <input type="number" id="height" name="height" required><br>

        <label for="weight">Weight (kg):</label>
        <input type="number" id="weight" name="weight" required><br>

        <input type="submit" value="Calculate">
    </form>

    <h2>Result:</h2>
    <c:if test="${not empty bmi}">
        <p>Your BMI: ${bmi}</p>
        <p>Category: ${category}</p>
    </c:if>

    <p><a href="history">View Calculation History</a></p>
    <p><a href="chat">Chat with other users</a></p>
    <p><a href="logout">Logout</a></p>
</body>
</html>

