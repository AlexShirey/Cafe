<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <title>Table user</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/admin/users.jsp"/>
<c:import url="/jsp/service/header.jsp"/>


<table class="table-admin" id="tableUsers" style="display: table">
    <thead>
    <tr>
        <th>id</th>
        <th>login/email</th>
        <th>first name</th>
        <th>last name</th>
        <th>phone</th>
        <%--<th>registration date</th>--%>
        <th>balance</th>
        <th>loyalty points</th>
        <th>active</th>
        <th>role</th>
        <th>edit</th>
        <th>orders</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}" varStatus="status">
        <tr>
            <td>${user.userId}</td>
            <td>${user.email}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.phone}</td>
            <td>${user.account.balance}</td>
            <td>${user.account.loyaltyPoints}</td>
            <td>${user.active}</td>
            <td>${user.role} </td>
            <td>
                <form method="get" action="${root}/controller">
                    <input type="hidden" name="command" value="show_edit_user_form">
                    <input type="hidden" name="userToEditId" value="${user.userId}">
                    <input type="submit" value="edit">
                </form>
            </td>
            <td>
                <form target="_blank" method="get" action="${root}/controller">
                    <input type="hidden" name="command" value="show_orders">
                    <input type="hidden" name="userIdToShowOrders" value="${user.userId}">
                    <input type="submit" value="show orders">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
