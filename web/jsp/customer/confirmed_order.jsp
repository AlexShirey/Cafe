<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.orderConfirmed"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/confirmed_order.jsp"/>


<div class="container text-center " style="margin-top: 250px">
    <c:if test="${messageNotEnoughMoney}">
        <span class="text-danger">
            <fmt:message key="message.notEnoughMoney" bundle="${rbMsg}"/></span><br>
        <c:remove var="messageNotEnoughMoney"/>
    </c:if>
    <c:if test="${messageNotEnoughLoyaltyPoints}">
    <span class="text-danger">
        <fmt:message key="message.notEnoughLoyaltyPoints" bundle="${rbMsg}"/></span><br>
        <c:remove var="messageNotEnoughMoney"/>
    </c:if>
    <c:if test="${messageMoneyWithdrawn}">
        <c:out value="${cartPrice}"/>
        <fmt:message key="message.moneyWithdrawn" bundle="${rbMsg}"/>
        <c:out value="${pointsForAccount} "/><fmt:message key="placeOrder.points"/>.<br><br>
        <span class="text-danger">
        <fmt:message key="message.orderCancelAccount" bundle="${rbMsg}"/></span>
        <c:remove var="messageMoneyWithdrawn"/>
    </c:if>
    <c:if test="${messageLoyaltyPointsTaken}">
        <fmt:message key="message.loyaltyPointsTaken" bundle="${rbMsg}"/><br><br>
        <span class="text-danger">
        <fmt:message key="message.orderCancelPoints" bundle="${rbMsg}"/></span>
        <c:remove var="messageLoyaltyPointsTaken"/>
    </c:if>
    <c:if test="${messagePayOnReceiving}">
        <c:out value="${cartPrice}"/>
        <fmt:message key="message.payOnReceiving" bundle="${rbMsg}"/>
        <c:out value="${pointsForCash} "/><fmt:message key="placeOrder.points"/>.<br><br>
        <span class="text-danger">
        <fmt:message key="message.orderCancelCash" bundle="${rbMsg}"/></span>
        <c:remove var="messagePayOnReceiving"/>
    </c:if>
    <c:if test="${messageOrderConfirmed}">
    <span class="text-danger">
        <ctg:formatDate date="${autoCancelDateTime}"/></span>
        <br>
        <br>
        <br>
        <hr style="border-color: #1e7e34">
        <span class="text-success">
        <fmt:message key="message.orderConfirmed" bundle="${rbMsg}"/></span><br>
        <br>
        <form method="get" action="${root}/controller">
            <input type="hidden" name="command" value="show_customer_page"/>
            <button type="submit" class="btn btn-outline-info my-2 my-sm-0"><fmt:message key="title.customerPage"/></button>
        </form>
        <c:remove var="messageOrderConfirmed"/>
    </c:if>

</div>

<br>
<br>


<c:import url="/jsp/service/footer.jsp"/>
</body>
