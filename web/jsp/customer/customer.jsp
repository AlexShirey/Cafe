<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.customerPage"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/customer.jsp"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">


    <c:if test="${!user.isActive()}">
    <span class="text-danger">
        <fmt:message key="menu.banned"/></span><br><br>
    </c:if>
    <c:if test="${empty orders}">
        ${user.firstName}, <fmt:message key="message.noOrders" bundle="${rbMsg}"/>
    </c:if>
    <c:if test="${not empty orders and user.isActive()}">
        <h5>${user.firstName}<fmt:message key="customerPage.info"/></h5><br>
    </c:if>
    <c:if test="${messageOrderIsAlreadyCancelled}">
    <span class="text-danger">
        <fmt:message key="message.orderIsAlreadyCancelled" bundle="${rbMsg}"/></span><br><br>
    </c:if>
    <c:if test="${messageOrderCancelled}">
    <span class="text-success">
        <fmt:message key="message.orderCancelled" bundle="${rbMsg}"/></span><br><br>
        <c:remove var="messageOrderCancelled"/>
    </c:if>
    <c:if test="${messageOrderPickedUp}">
    <span class="text-success">
        <fmt:message key="message.orderPickedUp" bundle="${rbMsg}"/></span><br><br>
        <c:remove var="messageOrderPickedUp"/>
    </c:if>
    <c:if test="${not empty activeOrders}">
        <h5><fmt:message key="customerPage.activeOrders"/></h5>
        <table class="table table-hover table-bordered">
            <thead class="thead-light text-uppercase">
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="customerPage.orderDate"/></th>
                <th scope="col"><fmt:message key="customerPage.orderDetails"/></th>
                <th scope="col"><fmt:message key="customerPage.orderPrice"/></th>
                <th scope="col"><fmt:message key="customerPage.orderPickUpTime"/></th>
                <th scope="col"><fmt:message key="customerPage.orderCancelPickUp"/></th>
            </tr>
            </thead>
            <tbody class="text-left">
            <c:forEach var="order" items="${activeOrders}" varStatus="status">
                <tr>
                    <td scope="row">${status.count}</td>
                    <td><ctg:formatDate date="${order.createDate}"/></td>
                    <td class="text-center">
                        <a class="btn btn-outline-info my-2 my-sm-0" href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                           target="_blank"><fmt:message key="customerPage.orderDetails"/></a>
                    </td>
                    <td>${order.orderPrice}</td>
                    <td><ctg:formatDate date="${order.pickUpTime}"/></td>
                    <td>
                        <div class="row justify-content-center">
                            <form method="post" action="${root}/controller">
                                <input type="hidden" name="command" value="finish_order"/>
                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                <c:choose>
                                    <c:when test="${currentTimeMillis > order.pickUpTime.getTime()}">
                                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="customerPage.pickUp"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"
                                                formaction="${root}/controller?command=cancel_order"><fmt:message key="customerPage.cancel"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${not empty cancelledOrders}">
        <br>
        <h5><fmt:message key="customerPage.cancelledOrders"/></h5>
        <table class="table table-hover table-bordered">
            <thead class="thead-light text-uppercase">
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="customerPage.orderDate"/></th>
                <th scope="col"><fmt:message key="customerPage.orderDetails"/></th>
                <th scope="col"><fmt:message key="customerPage.orderPrice"/></th>
                <th scope="col"><fmt:message key="customerPage.paymentType"/></th>
            </tr>
            </thead>
            <tbody class="text-left">
            <c:forEach var="order" items="${cancelledOrders}" varStatus="status">
                <tr>
                    <td scope="row">${status.count}</td>
                    <td><ctg:formatDate date="${order.createDate}"/></td>
                    <td class="text-center">
                        <a class="btn btn-outline-info my-2 my-sm-0" href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                           target="_blank"><fmt:message key="customerPage.orderDetails"/></a>
                    </td>
                    <td>${order.orderPrice}</td>
                    <td>${order.paymentType}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${not empty finishedOrders}">
        <br>
        <h5><fmt:message key="customerPage.finishedOrders"/></h5>
        <table class="table table-hover table-bordered">
            <thead class="thead-light text-uppercase">
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="customerPage.orderDate"/></th>
                <th scope="col"><fmt:message key="customerPage.orderDetails"/></th>
                <th scope="col"><fmt:message key="customerPage.orderPrice"/></th>
                <th scope="col"><fmt:message key="customerPage.paymentType"/></th>
                <th scope="col"><fmt:message key="customerPage.feedback"/></th>
            </tr>
            </thead>
            <tbody class="text-left">
            <c:forEach var="order" items="${finishedOrders}" varStatus="status">
                <tr>
                    <td scope="row">${status.count}</td>
                    <td><ctg:formatDate date="${order.createDate}"/></td>
                    <td class="text-center">
                        <a class="btn btn-outline-info my-2 my-sm-0" href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                           target="_blank"><fmt:message key="customerPage.orderDetails"/></a>
                    </td>
                    <td>${order.orderPrice}</td>
                    <td>${order.paymentType}</td>
                    <td>
                        <div class="row justify-content-center">
                            <form method="post" action="${root}/controller">
                                <input type="hidden" name="command" value="show_leave_feedback_page"/>
                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                <c:choose>
                                    <c:when test="${order.rating == 0}">
                                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="customerPage.btn.leaveFeedback"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit" disabled><fmt:message key="customerPage.btn.leaveFeedback"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>


