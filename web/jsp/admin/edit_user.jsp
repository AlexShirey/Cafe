<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Edit user</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/admin/edit_user.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<form method="post" action="${root}/controller">
    <fieldset>
        <legend>Edit user</legend>
        <input type="hidden" name="command" value="edit_user">
        <div class="form-element">
            <label>id</label>
            <input type="text" disabled placeholder="${userToEdit.userId}">
        </div>
        <div class="form-element">
            <label>login/email</label>
            <input type="text" disabled placeholder="${userToEdit.email}">
        </div>
        <div class="form-element">
            <label>first name</label>
            <input type="text" disabled placeholder="${userToEdit.firstName}">
        </div>
        <div class="form-element">
            <label>last name </label>
            <input type="text" disabled placeholder="${userToEdit.lastName}">
        </div>
        <div class="form-element">
            <label> phone </label>
            <input type="text" disabled placeholder="${userToEdit.phone}">
        </div>
        <div class="form-element">
            <label>registration date</label>
            <input type="text" disabled placeholder="<ctg:formatDate date="${userToEdit.createDate}"/>">
        </div>
        <div class="form-element">
            <label>account.balance </label>
            <input type="text" disabled placeholder="${userToEdit.account.balance}">
        </div>
        <div class="form-element">
            <label>account.loyalty points</label>
            <input required type="number" min="0" max="500" step="0.1" name="points"
                   value="${userToEdit.account.loyaltyPoints}">
        </div>
        <div class="form-element">
            <label>active</label>
            <select required name="active">
                <option selected>${userToEdit.active}</option>
                <c:if test="${userToEdit.active}">
                    <option value="false">false</option>
                </c:if>
                <c:if test="${!userToEdit.active}">
                    <option value="true">true</option>
                </c:if>
            </select>
        </div>
        <div class="form-element">
            <label>role</label>
            <select required name="role">
                <option selected value="${userToEdit.role.name()}">${userToEdit.role.name().toLowerCase()}</option>
                <c:forEach var="role" items="${userRoles}">
                    <c:if test="${role != userToEdit.role.name().toLowerCase()}">
                        <option value="${role}">${role}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <br>
        <c:choose>
            <c:when test="${userToEdit.userId != user.userId}">
                <input type="submit" value="update">
            </c:when>
            <c:otherwise>
                <c:out value="you are not allowed to edit yourself"/><br><br>
                <input disabled type="submit" value="update">
            </c:otherwise>
        </c:choose>
        <button type="reset" value="Reset">reset form</button>
    </fieldset>
</form>


<c:if test="${messageInvalidQuantity}">
    <fmt:message key="message.invalidQuantity" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageCantChangeRoleToAdmin}">
    <fmt:message key="message.cantChangeRoleToAdmin" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageUpdatedSuccessfully}">
    <fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/> <br> <br>
    <c:remove var="messageUpdatedSuccessfully"/>
    <a href="/controller?command=show_users">users</a>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
