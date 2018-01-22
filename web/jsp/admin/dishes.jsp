<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Table dish</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/admin/dishes.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<a href="#add">add dish</a>
<br>

<c:if test="${messageInvalidInputData}">
    <br><fmt:message key="message.invalidInputData" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageUpdatedSuccessfully}">
    <br><fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/>
    <c:remove var="messageUpdatedSuccessfully"/>
</c:if>


<table class="table-admin" id="tableDishes" style="display: table">
    <thead>
    <tr>
        <th>id</th>
        <th>dish type</th>
        <th>name</th>
        <th>description</th>
        <th>price</th>
        <th>in menu</th>
        <th>createDate</th>
        <th>edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="dish" items="${dishes}" varStatus="status">
        <tr>
            <td>${dish.dishId}</td>
            <td>${dish.type}</td>
            <td>${dish.name}</td>
            <td>${dish.description}</td>
            <td>${dish.price}</td>
            <td>${dish.isInMenu()}</td>
            <td><ctg:formatDate date="${dish.createDate}"/></td>
            <td>
                <form method="get" action="${root}/controller">
                    <input type="hidden" name="command" value="show_edit_dish_form">
                    <input type="hidden" name="dishToEditId" value="${dish.dishId}">
                    <input type="submit" value="edit">
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr id="add">
        <form method="post" action="${root}/controller">
            <input type="hidden" name="command" value="add_dish">
            <td>generated</td>
            <td>
                <select required name="type">
                    <c:forEach var="type" items="${dishTypes}">
                        <option value="${type}">${type}</option>
                    </c:forEach>
                </select>
            </td>


            <td><input required type="text" placeholder="name" name="name" pattern="[A-ZА-ЯЁa-zа-яё\d ]{2,}"
                       value=""><br>
                <small>2+ letters or digits</small>
            </td>
            <td><textarea placeholder="description" rows="3" cols="30" name="description"></textarea><br>
                <small>null or 5+, first char is a Capital letter</small>
            </td>
            <td><input required type="number" name="price" min="0.50" max="100" step="0.1" placeholder="price" value="">

            </td>
            <td>
                <select required name="inMenu">
                    <option value="true">true</option>
                    <option value="false">false</option>
                </select>
            </td>
            <td>generated</td>
            <td><input type="submit" value="add"></td>
        </form>
    </tr>
    </tbody>
</table>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
