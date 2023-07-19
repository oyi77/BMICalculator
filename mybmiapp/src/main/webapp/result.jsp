<%-- 
    Document   : result
    Created on : 17 Jul 2023, 17.42.26
    Author     : vpmac2
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BMI Calculation Result</title>
</head>
<body>
    <h1>BMI Calculation Result</h1>
    <p>
        Your BMI: <%= request.getAttribute("bmiValue") %>
    </p>
    <p>
        BMI Category: <%= request.getAttribute("bmiCategory") %>
    </p>
    <p>
        Weight Status: <%= request.getAttribute("weightStatus") %>
    </p>
    <p>
        Ideal Weight Range: <%= request.getAttribute("idealWeightRange") %>
    </p>
    <a href="calculator">Calculate Again</a>
</body>
</html>
