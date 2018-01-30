<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.login"/></title>
</head>
<body>
<c:set var="isLoginPage" value="true" scope="request"/>
<c:set var="currentPage" value="/jsp/guest/login.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>


<div class="container" style="margin-top: 270px">
    <div class="row justify-content-md-center"><br>
        <form name="loginForm" method="POST" action="${root}/controller">
            <input type="hidden" name="command" value="login"/>
            <div class="form-group">
                <label for="exampleInputEmail"><fmt:message key="login.login"/></label>
                <input type="text" name="login" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp"
                       placeholder="<fmt:message key="login.login.placeholder"/>" required>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword"><fmt:message key="login.password"/></label>
                <input type="password" name="password" class="form-control" id="exampleInputPassword"
                       placeholder="<fmt:message key="login.password.placeholder"/>" required>
                <small id="emailHelp" class="form-text text-muted"><fmt:message key="login.password.small"/></small>
            </div>
            <div class="row justify-content-md-center">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                        key="login.btn.login"/></button>
            </div>
            <c:if test="${messageIncorrectLoginOrPass}">
                <div class="row text-danger justify-content-center">
                    <br>
                    <fmt:message key="message.incorrectLoginOrPass" bundle="${rbMsg}"/>
                </div>
            </c:if>
        </form>
    </div>
</div>


<c:import url="/jsp/service/footer.jsp"/>
</body>