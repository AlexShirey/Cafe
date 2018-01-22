<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Menu</title>
</head>
<body>

<c:set var="currentPage" value="/jsp/reviews.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>

<br>

<c:if test="${messageReviewRemoved}">
    <br><fmt:message key="message.reviewRemoved" bundle="${rbMsg}"/><br>
    <c:remove var="messageReviewRemoved"/>
</c:if>


<c:if test="${empty ordersWithReview}">
    <fmt:message key="message.emptyReviews" bundle="${rbMsg}"/>
    <a href="${root}/controller?command=show_menu">menu</a>
</c:if>


<c:if test="${not empty ordersWithReview}">

    <table>
        <thead>
        <tr>
            <c:if test="${role == 'admin'}">
                <th>order id</th>
                <th>user id</th>
            </c:if>
            <th>date</th>
            <th>author</th>
            <th>rating</th>
            <th>review</th>
            <c:if test="${role == 'admin'}">
                <th>remove</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${ordersWithReview}">
            <tr>
                <c:if test="${role == 'admin'}">
                    <td>

                        <a href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                           target="_blank">${order.orderId}</a>
                    </td>
                    <td>
                        <a href="${root}/controller?command=show_edit_user_form&userToEditId=${order.userId}"
                           target="_blank">${order.userId}</a>
                    </td>
                </c:if>


                <td><ctg:formatDate date="${order.createDate}"/></td>
                <td>
                    <c:forEach var="customer" items="${customersWithReview}">
                        <c:if test="${customer.userId == order.userId}">
                            ${customer.firstName}
                        </c:if>
                    </c:forEach>
                </td>
                <td>${order.rating}</td>
                <td>${order.review}</td>
                <c:if test="${role == 'admin'}">
                    <td>
                        <form method="post" action="${root}/controller">
                            <input type="hidden" name="command" value="remove_review">
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            <input type="submit" value="remove">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
