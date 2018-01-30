<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.reviews"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" value="/jsp/reviews.jsp" scope="session"/>

<div class="container text-center justify-content-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <c:if test="${messageReviewRemoved}">
            <span class="text-success">
                <fmt:message key="message.reviewRemoved" bundle="${rbMsg}"/></span><br>
                <c:remove var="messageReviewRemoved"/>
            </c:if>
            <c:if test="${empty ordersWithReview}">
                <fmt:message key="message.emptyReviews" bundle="${rbMsg}"/><br>
                <hr style="border-color: #1e7e34">
                <a class="btn btn-outline-info my-2 my-sm-0" href="${root}/controller?command=show_menu">
                    <fmt:message key="reviews.menu"/></a>
            </c:if>
            <c:if test="${not empty ordersWithReview}">
                <br><h5><fmt:message key="title.reviews"/></h5><br>
                <table class="table table-hover table-bordered">
                    <thead class="thead-light text-uppercase">
                    <tr class="text-nowrap">
                        <c:if test="${role == 'admin'}">
                            <th scope="col"><fmt:message key="reviews.orderId"/></th>
                            <th scope="col"><fmt:message key="reviews.userId"/></th>
                        </c:if>
                        <th scope="col"><fmt:message key="reviews.date"/></th>
                        <c:if test="${role != 'admin'}">
                            <th scope="col"><fmt:message key="reviews.author"/></th>
                        </c:if>
                        <th scope="col"><fmt:message key="reviews.rating"/></th>
                        <th scope="col"><fmt:message key="reviews.review"/></th>
                        <c:if test="${role == 'admin'}">
                            <th scope="col"><fmt:message key="reviews.remove"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody class="text-left">
                    <c:forEach var="order" items="${ordersWithReview}">
                        <tr>
                            <c:if test="${role == 'admin'}">
                                <td scope="row" class="text-center">
                                        ${order.orderId}<br>
                                    <a class="btn btn-outline-info my-2 my-sm-0 btn-sm"
                                       href="${root}/controller?command=show_order_details&orderId=${order.orderId}&orderPrice=${order.orderPrice}"
                                       target="_blank">details</a>
                                </td>
                                <td class="text-center">
                                    <c:forEach var="customer" items="${customersWithReview}">
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
                            <c:if test="${role != 'admin'}">
                                <td>
                                    <c:forEach var="customer" items="${customersWithReview}">
                                        <c:if test="${customer.userId == order.userId}">
                                            ${customer.firstName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </c:if>
                            <td class="text-center">${order.rating}</td>
                            <td>${order.review}</td>
                            <c:if test="${role == 'admin'}">
                                <td>
                                    <div class="row justify-content-center">
                                        <form method="post" action="${root}/controller">
                                            <input type="hidden" name="command" value="remove_review">
                                            <input type="hidden" name="orderId" value="${order.orderId}">
                                            <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">
                                                <fmt:message key="reviews.remove"/>
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </c:if>
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

