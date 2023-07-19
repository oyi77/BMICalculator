<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chat</title>
</head>
<body>
    <h1>Chat</h1>

    <!-- Dropdown list to select a user -->
    <form action="chat" method="get">
        <label for="userSelect">Select a User:</label>
        <select name="receiverId" id="userSelect">
            <option value="" disabled selected>Select a user</option>
            <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.email}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Start Chat" />
    </form>

    <!-- Dropdown list to block a user -->
    <form action="blockUser" method="get">
        <label for="blockUserSelect">Block a User:</label>
        <select name="blockedUserId" id="blockUserSelect">
            <option value="" disabled selected>Select a user</option>
            <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.email}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Block User" />
    </form>

    <!-- Dropdown list to unblock a user -->
    <form action="unblockUser" method="get">
        <label for="unblockUserSelect">Unblock a User:</label>
        <select name="blockedUserId" id="unblockUserSelect">
            <option value="" disabled selected>Select a user</option>
            <c:forEach var="user" items="${blockedUsers}">
                <option value="${user.id}">${user.email}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Unblock User" />
    </form>

    <!-- Form to send messages -->
    <form action="chat" method="post">
        <input type="hidden" name="receiverId" value="${receiverId}" />
        <input type="hidden" name="email" value="${email}" />
        <textarea name="message" rows="4" cols="50" required></textarea>
        <br>
        <input type="submit" value="Send Chat" />
    </form>

    <!-- Display chat messages -->
    <c:if test="${not empty chatMessages}">
        <h2>Chat Messages with ${email}</h2>
        <ul>
            <c:forEach var="message" items="${chatMessages}">
                <li>${message.email}: ${message.message}</li>
            </c:forEach>
        </ul>
    </c:if>

    <p><a href="calculator">Back to Calculator</a></p>
    <p><a href="history">View Calculation History</a></p>
    <p><a href="logout">Logout</a></p>
</body>
</html>
