<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.dishes"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/admin/dishes.jsp"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <c:if test="${messageUpdatedSuccessfully}">
            <span class="text-success">
                <fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/></span><br>
                <c:remove var="messageUpdatedSuccessfully"/>
            </c:if>
            <c:if test="${messageInvalidInputData}">
            <span class="text-danger">
                <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/></span><br>
            </c:if>
            <br>
            <a class="btn btn-outline-info my-2 my-sm-0" href="#add"><fmt:message key="dishes.addTop"/></a>
            <h5 class="text-right"><fmt:message key="title.dishes"/></h5><br>
            <table class="table table-hover table-bordered">
                <thead class="thead-light text-uppercase">
                <tr>
                    <th scope="col">id</th>
                    <th scope="col"><fmt:message key="dishes.type"/></th>
                    <th scope="col"><fmt:message key="dishes.name"/></th>
                    <th scope="col"><fmt:message key="dishes.description"/></th>
                    <th scope="col"><fmt:message key="dishes.price"/></th>
                    <th scope="col"><fmt:message key="dishes.inMenu"/></th>
                    <th scope="col"><fmt:message key="dishes.createDate"/></th>
                    <th scope="col"><fmt:message key="dishes.edit"/></th>
                </tr>
                </thead>
                <tbody class="text-left">
                <c:forEach var="dish" items="${dishes}" varStatus="status">
                    <tr>
                        <td scope="row">${dish.dishId}</td>
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
                                <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                        key="dishes.edit"/></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <tr id="add">
                    <form method="post" action="${root}/controller">
                        <input type="hidden" name="command" value="add_dish">
                        <td><fmt:message key="dishes.generated"/></td>
                        <td>
                            <select required name="type">
                                <c:forEach var="type" items="${dishTypes}">
                                    <option value="${type}">${type}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input required type="text" placeholder="<fmt:message key="dishes.name"/>" name="name"
                                   pattern="[A-ZА-ЯЁa-zа-яё\d ]{2,50}"
                                   value=""><br>
                            <small><fmt:message key="dishes.name.small"/></small>
                        </td>
                        <td><textarea placeholder="<fmt:message key="dishes.description"/>" rows="2" cols="30"
                                      name="description"></textarea><br>
                            <small><fmt:message key="dishes.description.small"/></small>
                        </td>
                        <td><input required type="number" name="price" min="0.50" max="100" step="0.1"
                                   placeholder="<fmt:message key="dishes.price"/>" value="">
                        </td>
                        <td>
                            <select required name="inMenu">
                                <option value="true">true</option>
                                <option value="false">false</option>
                            </select>
                        </td>
                        <td><fmt:message key="dishes.generated"/></td>
                        <td>
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                                    key="dishes.add"/></button>
                        </td>
                    </form>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<br>
<br>


<c:import url="/jsp/service/footer.jsp"/>
</body>
