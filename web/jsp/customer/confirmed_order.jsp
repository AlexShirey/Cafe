<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Confirm Order</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/confirmed_order.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>

<c:if test="${messageNotEnoughMoney}">
    <fmt:message key="message.notEnoughMoney" bundle="${rbMsg}"/><br>
    <c:remove var="messageNotEnoughMoney"/>
</c:if>

<c:if test="${messageNotEnoughLoyaltyPoints}">
    <fmt:message key="message.notEnoughLoyaltyPoints" bundle="${rbMsg}"/><br>
    <c:remove var="messageNotEnoughMoney"/>
</c:if>

<c:if test="${messageMoneyWithdrawn}">
    <c:out value="${cartPrice}"/>
    <fmt:message key="message.moneyWithdrawn" bundle="${rbMsg}"/>
    <c:out value="${pointsForAccount} loyalty points."/><br><br>
    <fmt:message key="message.orderCancelAccount" bundle="${rbMsg}"/>
    <c:remove var="messageMoneyWithdrawn"/>
</c:if>

<c:if test="${messageLoyaltyPointsTaken}">
    <fmt:message key="message.loyaltyPointsTaken" bundle="${rbMsg}"/><br><br>
    <fmt:message key="message.orderCancelPoints" bundle="${rbMsg}"/>
    <c:remove var="messageLoyaltyPointsTaken"/>
</c:if>

<c:if test="${messagePayOnReceiving}">
    <c:out value="${cartPrice}"/>
    <fmt:message key="message.payOnReceiving" bundle="${rbMsg}"/>
    <c:out value="${pointsForCash} loyalty points."/><br><br>
    <fmt:message key="message.orderCancelCash" bundle="${rbMsg}"/>
    <c:remove var="messagePayOnReceiving"/>
</c:if>


<c:if test="${messageOrderConfirmed}">

    <ctg:formatDate date="${autoCancelDateTime}"/>
    <br>
    <br>
    <br>
    <hr>
    <fmt:message key="message.orderConfirmed" bundle="${rbMsg}"/><br>
    <br>
    <form method="get" action="${root}/controller">
        <input type="hidden" name="command" value="show_customer_page"/>
        <input type="submit" value="Customer page"/>
    </form>
    <c:remove var="messageOrderConfirmed"/>
</c:if>

<br>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
