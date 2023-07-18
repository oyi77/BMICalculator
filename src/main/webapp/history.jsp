<%-- 
    Document   : history.jsp
    Created on : 17 Jul 2023, 11.13.04
    Author     : vpmac2
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Calculation History - BMI Calculator</title>
    </head>
    <body>
        <h1>Calculation History</h1>

        <table border="1">
            <tr>
                <th>Date</th>
                <th>Height (cm)</th>
                <th>Weight (kg)</th>
                <th>BMI</th>
                <th>Category</th>
                <th>Gender</th> <!-- Add the gender column header -->
            </tr>

            <c:forEach var="calculation" items="${calculations}">
                <tr>
                    <td>${calculation.timestamp}</td>
                    <td>${calculation.height}</td>
                    <td>${calculation.weight}</td>
                    <td>${calculation.bmi}</td>
                    <td>${calculation.category}</td>
                    <td>${calculation.gender}</td> <!-- Display the gender for each calculation -->
                </tr>
            </c:forEach>
        </table>

        <p><a href="calculator">Back to Calculator</a></p>
        <p><a href="chat">Chat with other users</a></p>
        <p><a href="logout">Logout</a></p>
    </body>
</html
