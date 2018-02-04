<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.cart"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/cart.jsp"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">
    <c:if test="${empty cart}">
        <fmt:message key="message.emptyCart" bundle="${rbMsg}"/><br><hr style="border-color: #1e7e34">
        <a class="btn btn-outline-info my-2 my-sm-0" href="${root}/controller?command=show_menu">
           <fmt:message key="reviews.menu"/></a>
    </c:if>
    <c:if test="${messageMenuChanged}">
    <span class="text-danger">
        <fmt:message key="message.menuChanged" bundle="${rbMsg}"/></span>
        <c:remove var="messageMenuChanged"/>
    </c:if>
    <c:if test="${not empty cart}">
        <div class="row">
            <div class="col">
                <br>
                <h5><fmt:message key="title.cart"/></h5><br>
                <table class="table table-hover table-bordered">
                    <thead class="thead-light text-uppercase">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="cart.category"/></th>
                        <th scope="col"><fmt:message key="cart.name"/></th>
                        <th scope="col"><fmt:message key="cart.quantity"/></th>
                        <th scope="col"><fmt:message key="cart.price"/></th>
                        <th scope="col"><fmt:message key="cart.remove"/></th>
                    </tr>
                    </thead>
                    <tbody class="text-left">
                    <c:forEach var="cart" items="${cart}" varStatus="status">
                        <tr>
                            <td scope="row">${status.count}</td>
                            <td>${cart.key.type.name()}</td>
                            <td>${cart.key.name}</td>
                            <td class="text-right">${cart.value}</td>
                            <td class="text-right">${cart.key.price}</td>
                            <td>
                                <form name="removeDishFromCartForm" method="post" action="${root}/controller">
                                    <div class="form-row justify-content-center">
                                        <input type="hidden" name="command" value="remove_dish_from_cart"/>
                                        <input type="hidden" name="dishId" value="${cart.key.dishId}">
                                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message key="cart.btn.remove"/>
                                        </button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr class="text-right">
                        <th scope="row" colspan="4"><fmt:message key="cart.totalPrice"/>:</th>
                        <th colspan="2">${cartPrice}</th>
                    </tr>
                    </tfoot>
                </table>
                <br>
                <form name="placeOrderForm" method="get" action="${root}/controller">
                    <input type="hidden" name="command" value="show_place_order_page"/>
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="cart.placeOrder"/></button>
                </form>
            </div>
        </div>
    </c:if>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>




