<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Registration</title>
</head>
<body>

<c:set var="currentPage" value="/jsp/guest/registration.jsp" scope="session"/>
<c:set var="isRegistrationPage" value="true" scope="request"/>
<c:import url="/jsp/service/header.jsp"/>

<%--TODO add * to the fields--%>
<br>
<form name="registrationForm" autocomplete="on" method="post" action="${root}/controller">
    <fieldset>
        <legend>Personal information:</legend>
        <input type="hidden" name="command" value="register">

        <input type="text" name="login" placeholder="email@server.domain" pattern="[a-z0-9._%+-]{1,20}@[a-z0-9.-]{1,16}\.[a-z]{2,3}$" required><br>
        <small>pattern characters@characters.domain (2-3 chars for domain, max 40 chars)</small><br>

        <input type="password" name="password" id="password" placeholder="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}" required autocomplete="off"><br>
        <small>6+ chars (at least one number, one upper and lower case letter, max 30 chars)</small><br>

        <input type="password" id="confirm_password" placeholder="confirm password" required autocomplete="off"><br>
        <small>confirm password, please</small><br>

        <input type="text" name="first_name" placeholder="your first name, please" pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}" required><br>
        <small>2+ chars (letters only, first letter is Capital, max 20 chars (RU or EN))</small><br>

        <input type="text" name="last_name" placeholder="your last name, please" pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}" required><br>
        <small>2+ chars (letters only, first letter is Capital, max 20 chars (RU or EN))</small><br>

        <input type="text" name="phone" placeholder="phone, please" pattern="[+]\d{3}[(]\d{2}[)]\d{3}[-]\d{2}[-]\d{2}" required><br>
        <small>BY format +375(29)612-61-09</small><br>
        <br>
        <input type="submit" class="form-btn" value="Register">
    </fieldset>
</form>


<c:if test="${messageInvalidInputData}">
    <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageLoginAlreadyExist}">
    <fmt:message key="message.loginAlreadyExist" bundle="${rbMsg}"/>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
