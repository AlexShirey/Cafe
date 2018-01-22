<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Table order</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/admin/orders.jsp"/>
<c:import url="/jsp/service/header.jsp"/>


<c:if test="${empty orders}">
    <br>no orders
</c:if>


<c:if test="${messageOrderIsAlreadyCancelled}">
    <br><fmt:message key="message.orderIsAlreadyCancelled" bundle="${rbMsg}"/><br>
</c:if>
<c:if test="${messageOrderCancelled}">
    <br><fmt:message key="message.orderCancelled" bundle="${rbMsg}"/><br>
    <c:remove var="messageOrderCancelled"/>
</c:if>

<c:if test="${messageOrderFinished}">
    <br><fmt:message key="message.orderFinished" bundle="${rbMsg}"/><br>
    <c:remove var="messageOrderFinished"/>
</c:if>

<c:if test="${not empty orders}">
    <table class="table-admin">
        <thead>
        <tr>
            <th>order id</th>
            <th>user id</th>
            <th>create date</th>
            <th>payment type</th>

            <th>order price</th>
            <th>is paid</th>
            <th>pick up date</th>
            <th>status</th>

            <%--<th>rating</th>--%>
            <%--<th>review</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}" varStatus="status">
            <tr>
                <td>

                    <a href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                       target="_blank">${order.orderId}</a>
                </td>

                <td>
                    <a href="${root}/controller?command=show_edit_user_form&userToEditId=${order.userId}"
                       target="_blank">${order.userId}</a>
                </td>

                        <%--<form method="get" action="${root}/controller">--%>
                        <%--<input type="hidden" name="command" value="show_edit_user_form">--%>
                        <%--<input type="hidden" name="userToEditId" value="${user.userId}">--%>
                        <%--<input type="submit" value="edit">--%>
                        <%--</form>--%>



                <td><ctg:formatDate date="${order.createDate}"/></td>
                <td>${order.paymentType}</td>

                <td>${order.orderPrice}</td>
                <td>${order.isPaid()}</td>
                <td><ctg:formatDate date="${order.pickUpTime}"/></td>
                <td>${order.status}
                    <c:if test="${order.status == 'ACTIVE'}">
                        <form method="post" action="${root}/controller">
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            <button type="submit" formaction="${root}/controller?command=cancel_order">cancel</button>
                            <button type="submit" formaction="${root}/controller?command=finish_order">finish</button>
                        </form>
                    </c:if>
                </td>

                <%--<td>--%>
                    <%--<c:choose>--%>
                        <%--<c:when test="${order.rating == 0}">--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>${order.rating}</c:otherwise>--%>
                    <%--</c:choose>--%>
                <%--</td>--%>
                <%--<td>${order.review}--%>
                    <%--<c:if test="${not empty order.review}">--%>
                        <%--<form method="post" action="${root}/controller">--%>
                            <%--<input type="hidden" name="command" value="remove_review">--%>
                            <%--<input type="hidden" name="page" value="/jsp/admin/orders.jsp">--%>
                            <%--<input type="hidden" name="orderId" value="${order.orderId}">--%>
                            <%--<input type="submit" value="remove review">--%>
                        <%--</form>--%>
                    <%--</c:if>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
