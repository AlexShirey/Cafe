<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer page</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/customer.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>

<c:if test="${!user.isActive()}">
    YOU ARE BANNED!!!
</c:if>

<c:if test="${empty orders}">
    ${user.firstName}, <fmt:message key="message.noOrders" bundle="${rbMsg}"/>
</c:if>

<c:if test="${not empty orders and user.isActive()}">
    <p> Here you can manage and browse you orders. </p>
</c:if>


<c:if test="${messageOrderIsAlreadyCancelled}">
    <br><fmt:message key="message.orderIsAlreadyCancelled" bundle="${rbMsg}"/><br>
</c:if>
<c:if test="${messageOrderCancelled}">
    <br><fmt:message key="message.orderCancelled" bundle="${rbMsg}"/><br>
    <c:remove var="messageOrderCancelled"/>
</c:if>
<c:if test="${messageOrderPickedUp}">
    <br><fmt:message key="message.orderPickedUp" bundle="${rbMsg}"/><br>
    <c:remove var="messageOrderPickedUp"/>
</c:if>

<c:if test="${not empty activeOrders}">
    <h4>Active orders</h4>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>order date</th>
            <th>details</th>
            <th>order price</th>
            <th>pick up time</th>
            <th>cancel/pick up</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="order" items="${activeOrders}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><ctg:formatDate date="${order.createDate}"/></td>
                <td>
                    <a href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                       target="_blank">details</a>
                </td>
                <td>${order.orderPrice}</td>
                <td><ctg:formatDate date="${order.pickUpTime}"/></td>
                <td>
                    <form method="post" action="${root}/controller">
                        <input type="hidden" name="command" value="finish_order"/>
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <c:choose>
                            <c:when test="${currentTimeMillis > order.pickUpTime.getTime()}">
                                <button type="submit">pick up</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" formaction="${root}/controller?command=cancel_order">cancel
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
            <%--<tfoot>--%>
            <%--<tr>--%>
            <%--<td colspan="4">Total price:</td>--%>
            <%--<td colspan="2">${cartPrice} </td>--%>
            <%--</tr>--%>
            <%--</tfoot>--%>
    </table>
</c:if>


<c:if test="${not empty cancelledOrders}">
    <h4>Cancelled orders</h4>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>order date</th>
            <th>details</th>
            <th>order price</th>
            <th>payment type</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${cancelledOrders}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><ctg:formatDate date="${order.createDate}"/></td>
                <td>
                    <a href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                       target="_blank">details</a>
                </td>
                <td>${order.orderPrice}</td>
                <td>${order.paymentType}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:if test="${not empty finishedOrders}">
    <h4>Finished orders</h4>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>order date</th>
            <th>details</th>
            <th>order price</th>
            <th>payment type</th>
            <th>feedback</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="order" items="${finishedOrders}" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><ctg:formatDate date="${order.createDate}"/></td>
                <td>
                    <a href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                       target="_blank">details</a>
                </td>
                <td>${order.orderPrice}</td>
                <td>${order.paymentType}</td>
                <td>
                    <form method="post" action="${root}/controller">
                        <input type="hidden" name="command" value="show_leave_feedback_page"/>
                        <input type="hidden" name="orderId" value="${order.orderId}"/>
                        <c:choose>

                            <c:when test="${order.rating == 0}">
                                <%--<button type="submit" formaction="/action_page2.php">Submit to another page</button>--%>
                                <input type="submit" value="leave feedback">
                            </c:when>
                            <c:otherwise>
                                <%--<button type="submit" disabledformaction="/action_page2.php">Submit to another page</button>--%>
                                <input type="submit" disabled value="leave feedback">
                            </c:otherwise>
                        </c:choose>
                    </form>

                        <%--<form method="post" action="${root}jsp/customer/leave_feedback.jsp">--%>
                        <%--&lt;%&ndash;<input type="hidden" name="command" value="show_leave_feedback_page"/>&ndash;%&gt;--%>
                        <%--<input type="hidden" name="orderId" value="${order.orderId}"/>--%>
                        <%--<c:choose>--%>
                        <%--<c:when test="${not empty order.rating}">--%>
                        <%--&lt;%&ndash;<button type="submit" formaction="/action_page2.php">Submit to another page</button>&ndash;%&gt;--%>
                        <%--<input type="submit" value="leave feedback">--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                        <%--&lt;%&ndash;<button type="submit" disabledformaction="/action_page2.php">Submit to another page</button>&ndash;%&gt;--%>
                        <%--<input type="submit" disabled value="leave feedback">--%>
                        <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                        <%--</form>--%>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>

