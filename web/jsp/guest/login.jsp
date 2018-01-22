<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Login</title>
</head>
<body>

<c:set var="currentPage" value="/jsp/guest/login.jsp" scope="session"/>
<c:set var="isLoginPage" value="true" scope="request"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<form name="loginForm" method="POST" action="${root}/controller">
    <input type="hidden" name="command" value="login"/>
    Login (email) или мыло по русски:<br/>
    <input type="text" name="login"/>
    <br/>Password:<br/>
    <input type="password" name="password"/>
    <br>
    <c:if test="${messageIncorrectLoginOrPass}">
        <fmt:message key="message.incorrectLoginOrPass" bundle="${rbMsg}"/>
    </c:if>
    <br>
    <input type="submit" value="Log in"/>
</form>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>