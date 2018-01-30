<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.orders"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/admin/orders.jsp"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <c:if test="${empty orders}">
                <fmt:message key="orders.noOrders"/>
            </c:if>
            <c:if test="${messageOrderIsAlreadyCancelled}">
                <span class="text-danger">
                    <fmt:message key="message.orderIsAlreadyCancelled" bundle="${rbMsg}"/></span>
            </c:if>
            <c:if test="${messageOrderCancelled}">
                <span class="text-success">
                    <fmt:message key="message.orderCancelled" bundle="${rbMsg}"/></span>
                <c:remove var="messageOrderCancelled"/>
            </c:if>
            <c:if test="${messageOrderFinished}">
                <span class="text-success">
                    <fmt:message key="message.orderFinished" bundle="${rbMsg}"/></span>
                <c:remove var="messageOrderFinished"/>
            </c:if>
            <c:if test="${not empty orders}">
                <br><h5 class="text-right"><fmt:message key="title.orders"/>
                <c:if test="${userToShowOrders != null}">
                    <fmt:message key="orders.for"/> <br><br>
                    ${userToShowOrders.firstName} ${userToShowOrders.lastName}, id = ${userToShowOrders.userId}<br>
                    ${userToShowOrders.phone}
                </c:if>
            </h5><br>
                <table class="table table-hover table-bordered">
                    <thead class="thead-light text-uppercase">
                    <tr>
                        <th scope="col"><fmt:message key="orders.orderId"/></th>
                        <c:if test="${userToShowOrders == null}">
                            <th scope="col"><fmt:message key="orders.userId"/></th>
                        </c:if>
                        <th scope="col"><fmt:message key="orders.createDate"/></th>
                        <th scope="col"><fmt:message key="orders.paymentType"/></th>
                        <th scope="col"><fmt:message key="orders.orderPrice"/></th>
                        <th scope="col"><fmt:message key="orders.isPaid"/></th>
                        <th scope="col"><fmt:message key="orders.pickUpDate"/></th>
                        <th scope="col"><fmt:message key="orders.status"/></th>
                    </tr>
                    </thead>
                    <tbody class="text-left">
                    <c:forEach var="order" items="${orders}" varStatus="status">
                        <tr>
                            <td scope="row" class="text-center">
                                    ${order.orderId}<br>
                                <a class="btn btn-sm btn-outline-info my-2 my-sm-0"
                                   href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                                   target="_blank">details</a>
                            </td>
                            <c:if test="${userToShowOrders == null}">
                                <td class="text-center">
                                    <c:forEach var="customer" items="${usersWithOrders}">
                                        <c:if test="${customer.userId == order.userId}">
                                            ${customer.firstName} ${customer.lastName} <br>
                                            <span class="text-nowrap">${customer.phone}</span><br>
                                        </c:if>
                                    </c:forEach>
                                    <a class="btn btn-outline-info my-2 my-sm-0 btn-sm"
                                       href="${root}/controller?command=show_edit_user_form&userToEditId=${order.userId}"
                                       target="_blank">edit user id ${order.userId}</a>
                                </td>
                            </c:if>
                            <td><ctg:formatDate date="${order.createDate}"/></td>
                            <td>${order.paymentType}</td>
                            <td>${order.orderPrice}</td>
                            <td>${order.isPaid()}</td>
                            <td><ctg:formatDate date="${order.pickUpTime}"/></td>
                            <td class="text-center">${order.status}
                                <c:if test="${order.status == 'ACTIVE'}">
                                    <form method="post" action="${root}/controller">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <button class="btn btn-sm btn-outline-danger my-2 my-sm-0" type="submit"
                                                formaction="${root}/controller?command=cancel_order">
                                            <small><fmt:message key="orders.cancel"/></small>
                                        </button>
                                        <button class="btn btn-sm btn-outline-success my-2 my-sm-0" type="submit"
                                                formaction="${root}/controller?command=finish_order">
                                            <small><fmt:message key="orders.finish"/></small>
                                        </button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>


<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>
