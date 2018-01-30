<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.orderDetails"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/order_details.jsp"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">

    <div class="row">
        <div class="col">

            <br><br>
            <h5><fmt:message key="title.orderDetails"/></h5><br>
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
                <c:forEach var="dish" items="${dishesInOrder}" varStatus="status">
                    <tr>
                        <td scope="row">${status.count}</td>
                        <td>${dish.key.type.name()}</td>
                        <td>${dish.key.name}</td>
                        <td class="text-right">${dish.value}</td>
                        <td class="text-right">${dish.key.price}</td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr class="text-right">
                    <th scope="row" colspan="4"><fmt:message key="cart.totalPrice"/></th>
                    <th colspan="2">${orderPrice} </th>
                </tr>
                </tfoot>
            </table>

        </div>
    </div>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>


