<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer page</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/order_details.jsp"/>
<c:import url="/jsp/service/header.jsp"/>


<table>
    <thead>
    <tr>
        <th>#</th>
        <th>category</th>
        <th>dish name</th>
        <th>dish price</th>
        <th>quantity</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="dish" items="${dishesInOrder}" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${dish.key.type.name()}</td>
            <td>${dish.key.name}</td>
            <td>${dish.key.price}</td>
            <td>${dish.value}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td colspan="4">Order price:</td>
        <td colspan="2">${orderPrice} </td>
    </tr>
    </tfoot>
</table>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>

