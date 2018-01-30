<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.placeOrder"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/place_order.jsp"/>

<div class="container text-center justify-content-center" style="margin-top: 250px">
    <div class="row justify-content-center">
        <div class="col">
            <br>
            <table class="table table-hover table-bordered">
                <thead class="thead-light text-uppercase">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="cart.category"/></th>
                    <th scope="col"><fmt:message key="cart.name"/></th>
                    <th scope="col"><fmt:message key="cart.quantity"/></th>
                    <th scope="col"><fmt:message key="cart.price"/></th>
                </tr>
                </thead>
                <tbody class="text-left">
                <c:forEach var="cart" items="${cart}" varStatus="status">
                    <tr>
                        <td scope="row">${status.count}</td>
                        <td>${cart.key.type.name()}</td>
                        <td>${cart.key.name}</td>
                        <td class="text-right">${cart.key.price}</td>
                        <td class="text-right"> ${cart.value}</td>

                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr class="text-right">
                    <th scope="row" colspan="4"><fmt:message key="cart.totalPrice"/></th>
                    <th>${cartPrice} </th>
                </tr>
                </tfoot>
            </table>
            <br>
            <form name="OrderInputInfoForm" method="post" action="${root}/controller">
                <legend><fmt:message key="placeOrder.chooseType"/></legend>
                <input type="hidden" name="command" value="confirm_order"/>
                <div class="row justify-content-center">
                    <div class="col-5">
                        <div class="custom-control custom-radio">
                            <input type="radio" id="customRadio1" name="paymentType" class="custom-control-input"
                                   value="account" required>
                            <label class="custom-control-label" for="customRadio1"><fmt:message key="placeOrder.account"/> ${pointsForAccount}
                                <fmt:message key="placeOrder.points"/></label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input type="radio" id="customRadio2" name="paymentType" class="custom-control-input"
                                   value="cash"
                                   required>
                            <label class="custom-control-label" for="customRadio2"><fmt:message key="placeOrder.cash"/> ${pointsForCash} <fmt:message key="placeOrder.points"/></label>
                        </div>
                        <div class="custom-control custom-radio">
                            <c:choose>
                                <c:when test="${user.account.loyaltyPoints.compareTo(cartPrice) < 0}">
                                    <input type="radio" id="customRadio3" name="paymentType"
                                           class="custom-control-input"
                                           value="loyalty_points" disabled>
                                    <label class="custom-control-label" for="customRadio3"><fmt:message key="placeOrder.usePointsNotEnough1"/> ${user.account.loyaltyPoints}<fmt:message key="placeOrder.usePointsNotEnough2"/></label>
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" id="customRadio3" name="paymentType"
                                           class="custom-control-input"
                                           value="loyalty_points" required>
                                    <label class="custom-control-label" for="customRadio3"><fmt:message key="placeOrder.usePoints"/></label>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <br><br>
                <legend><fmt:message key="placeOrder.whenPickUp"/></legend>
                <div class="row justify-content-center">
                    <div class="col-3">
                        <input type="datetime-local" class="form-control" name="pickUpTime" min="${minPickUpTime}"
                               max="${maxPickUpTimeTime}"
                               required>
                    </div>
                </div>
                <br><hr style="border-color: #1e7e34">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="placeOrder.btn.confirm"/></button>
            </form>
        </div>
    </div>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>

