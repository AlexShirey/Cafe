<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.registration"/></title>
</head>
<body>
<c:set var="isRegistrationPage" value="true" scope="request"/>
<c:set var="currentPage" value="/jsp/guest/registration.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>


<div class="container" style="margin-top: 230px">
    <div class="row justify-content-md-center">
        <form class="form-control-sm" method="post" action="${root}/controller">
            <input type="hidden" name="command" value="register">
            <div class="form-group">
                <label for="login"><fmt:message key="registration.login"/></label>
                <input type="text" name="login" class="form-control" id="login" aria-describedby="emailHelp"
                       placeholder="<fmt:message key="registration.login.placeholder"/>"
                       pattern="[a-z0-9._%+-]{1,20}@[a-z0-9.-]{1,16}\.[a-z]{2,3}$" required>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="registration.login.small"/>
                </small>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="registration.password"/></label>
                <input type="password" name="password" class="form-control" id="password"
                       aria-describedby="passwordHelp"
                       placeholder="<fmt:message key="registration.password.placeholder"/>"
                       pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}" required>
                <small id="passwordHelp" class="form-text text-muted"><fmt:message key="registration.password.small"/>
                </small>
            </div>
            <div class="form-group">
                <label for="confirm_password"><fmt:message key="registration.confirm"/></label>
                <input type="password" class="form-control" id="confirm_password" aria-describedby="confPasswordHelp"
                       placeholder="<fmt:message key="registration.confirm.placeholder"/>"
                       pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}" required>
                <small id="confPasswordHelp" class="form-text text-muted"><fmt:message
                        key="registration.confirm.small"/>
                </small>
            </div>
            <div class="form-group">
                <label for="first_name"><fmt:message key="registration.first"/></label>
                <input type="text" name="first_name" class="form-control" id="first_name"
                       aria-describedby="firstNameHelp"
                       placeholder="<fmt:message key="registration.first.placeholder"/>"
                       pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}"
                       required>
                <small id="firstNameHelp" class="form-text text-muted"><fmt:message key="registration.first.small"/>
                </small>
            </div>
            <div class="form-group">
                <label for="last_name"><fmt:message key="registration.last"/></label>
                <input type="text" name="last_name" class="form-control" id="last_name" aria-describedby="lastNameHelp"
                       placeholder="<fmt:message key="registration.last.placeholder"/>"
                       pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}"
                       required>
                <small id="lastNameHelp" class="form-text text-muted"><fmt:message key="registration.last.small"/>
                </small>
            </div>
            <div class="form-group">
                <label for="phone"><fmt:message key="registration.phone"/></label>
                <input type="text" name="phone" class="form-control" id="phone" aria-describedby="phoneHelp"
                       placeholder="<fmt:message key="registration.phone.placeholder"/>"
                       pattern="[+]\d{3}[(]\d{2}[)]\d{3}[-]\d{2}[-]\d{2}"
                       required>
                <small id="phoneHelp" class="form-text text-muted"><fmt:message key="registration.phone.small"/>
                </small>
            </div>
            <div class="row justify-content-md-center">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                        key="registration.btn.register"/></button>
            </div>
        </form>
    </div>
    <div class="row justify-content-md-center">
        <c:if test="${messageInvalidInputData}">
            <span class="text-danger">
                <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/></span>
        </c:if>
        <c:if test="${messageLoginAlreadyExist}">
        <span class="text-danger">
            <fmt:message key="message.loginAlreadyExist" bundle="${rbMsg}"/></span>
        </c:if>
    </div>
</div>


<c:import url="/jsp/service/footer.jsp"/>
<script type="text/javascript" src="${root}/js/cafe.js"></script>
</body>

