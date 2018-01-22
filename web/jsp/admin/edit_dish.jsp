<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Edit dish</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/admin/edit_dish.jsp"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<form method="post" action="${root}/controller">
    <fieldset>
        <legend>Edit dish</legend>
        <input type="hidden" name="command" value="edit_dish">
        <div class="form-element">
            <label>id</label>
            <input type="text" disabled placeholder="${dishToEdit.dishId}">
        </div>
        <div class="form-element">
            <label>type</label>
            <input type="text" disabled placeholder="${dishToEdit.type}">
        </div>
        <div class="form-element">
            <label>name</label>
            <input type="text" disabled placeholder="${dishToEdit.name}">
        </div>
        <div class="form-element">
            <label> create date </label>
            <input type="text" disabled placeholder="<ctg:formatDate date="${dishToEdit.createDate}"/>">
        </div>
        <div class="form-element">
            <label class="textarea-center" for="textarea">description</label>
            <textarea id="textarea" rows="4" cols="50" name="description">${dishToEdit.description}</textarea><br>
            <small>null or 5+, first char is a Capital letter</small>
        </div>
        <div class="form-element">
            <label> price </label>
            <input required type="number" min="0.50" max="100" step="0.1" name="price"
                   value="${dishToEdit.price}">
        </div>
        <div class="form-element">
            <label>in menu</label>
            <select required name="inMenu">
                <option selected>${dishToEdit.inMenu}</option>
                <c:if test="${dishToEdit.inMenu}">
                    <option value="false">false</option>
                </c:if>
                <c:if test="${!dishToEdit.inMenu}">
                    <option value="true">true</option>
                </c:if>
            </select>
        </div>
        <br>

        <input type="submit" value="update">
        <button type="reset" value="Reset">reset form</button>

    </fieldset>
</form>

<c:if test="${messageInvalidInputData}">
    <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/>
</c:if>

<c:if test="${messageUpdatedSuccessfully}">
    <fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/><br><br>
    <c:remove var="messageUpdatedSuccessfully"/>
    <a href="/controller?command=show_dishes">dishes</a>
</c:if>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
