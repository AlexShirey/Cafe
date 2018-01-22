<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Shopping cart</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/cart.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<c:if test="${empty cart}">
    <fmt:message key="message.emptyCart" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageMenuChanged}">
    <br>
    <fmt:message key="message.menuChanged" bundle="${rbMsg}"/>
    <c:remove var="messageMenuChanged"/>
    <br>

</c:if>

<c:if test="${not empty cart}">
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>category</th>
            <th>dish name</th>
            <th>dish price</th>
            <th>quantity</th>
            <th>remove</th>
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
                <td>
                    <form name="removeDishFromCartForm" method="post" action="${root}/controller">
                        <input type="hidden" name="command" value="remove_dish_from_cart"/>
                        <input type="hidden" name="dishId" value="${cart.key.dishId}">
                        <input type="submit" value="remove dish"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="4">Total price:</td>
            <td colspan="2">${cartPrice} </td>
        </tr>
        </tfoot>
    </table>
</c:if>

<c:if test="${not empty cart}">
    <form name="placeOrderForm" method="get" action="${root}/controller">
        <input type="hidden" name="command" value="place_order"/>
        <input type="submit" value="place order"/>
    </form>
</c:if>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>



