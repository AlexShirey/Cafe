<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.users"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/admin/users.jsp"/>


<div class="container text-center" style="margin-top: 250px">
    <div class="row justify-content-center">
        <div class="col">
            <br><h5 class="text-right"><fmt:message key="title.users"/></h5><br>
            <table class="table table-hover table-bordered">
                <thead class="thead-light text-uppercase">
                <tr>
                    <th scope="col">id</th>
                    <th scope="col"><fmt:message key="users.loginEmail"/></th>
                    <th scope="col"><fmt:message key="profile.first"/></th>
                    <th scope="col"><fmt:message key="profile.last"/></th>
                    <th scope="col"><fmt:message key="users.phone"/></th>
                    <th scope="col"><fmt:message key="profile.balance"/></th>
                    <th scope="col"><fmt:message key="users.points"/></th>
                    <th scope="col"><fmt:message key="users.active"/></th>
                    <th scope="col"><fmt:message key="users.role"/></th>
                    <th scope="col"><fmt:message key="users.edit"/></th>
                    <th scope="col"><fmt:message key="users.userOrders"/></th>
                </tr>
                </thead>
                <tbody class="text-left">
                <c:forEach var="user" items="${users}" varStatus="status">
                    <tr>
                        <td scope="row">${user.userId}</td>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td class="text-nowrap">${user.phone}</td>
                        <td>${user.account.balance}</td>
                        <td>${user.account.loyaltyPoints}</td>
                        <td>${user.active}</td>
                        <td>${user.role} </td>
                        <td>
                            <form method="get" action="${root}/controller">
                                <input type="hidden" name="command" value="show_edit_user_form">
                                <input type="hidden" name="userToEditId" value="${user.userId}">
                                <button class="btn btn-sm btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message key="users.edit"/>
                                </button>
                            </form>
                        </td>
                        <td>
                            <form target="_blank" method="get" action="${root}/controller">
                                <input type="hidden" name="command" value="show_orders">
                                <input type="hidden" name="userIdToShowOrders" value="${user.userId}">
                                <button class="btn btn-sm btn-outline-info my-2 my-sm-0" type="submit"><fmt:message key="users.userOrders"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>

