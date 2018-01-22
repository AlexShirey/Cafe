<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Place Order</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/place_order.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<table>
    <thead>
    <tr>
        <th>#</th>
        <th>category</th>
        <th>dish name</th>
        <th>dish price</th>
        <th>quantity</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="cart" items="${cart}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${cart.key.type.name()}</td>
            <td>${cart.key.name}</td>
            <td>${cart.key.price}</td>
            <td>${cart.value}</td>

        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="4">Total price:</td>
        <td>${cartPrice} </td>
    </tr>
    </tfoot>
</table>

<hr>

<form name="OrderInputInfoForm" method="post" action="${root}/controller">
    <h3> Choose payment type, please </h3>
    <input type="hidden" name="command" value="confirm_order"/>
    <div class="radio">
        <input type="radio" name="paymentType" required
               value="account">Account - you will get ${pointsForAccount} loyalty points<br>
        <input type="radio" name="paymentType" required
               value="cash">Cash (on receiving) - you will get ${pointsForCash} loyalty points<br><br>
        <c:choose>
            <c:when test="${user.account.loyaltyPoints.compareTo(cartPrice) < 0}">
                <input type="radio" name="paymentType" disabled
                       value="loyalty_points">Use loyalty points (you have ${user.account.loyaltyPoints}, not enough)<br>
            </c:when>
            <c:otherwise>
                <input type="radio" name="paymentType" required value="loyalty_points">Use loyalty points<br>
            </c:otherwise>
        </c:choose>
    </div>
    <h3>When do you want to pick up the order?</h3>
    <input type="datetime-local" name="pickUpTime" min="${minPickUpTime}" max="${maxPickUpTimeTime}" required>
    <br><br>
    <input type="submit" value="confirm">
</form>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
