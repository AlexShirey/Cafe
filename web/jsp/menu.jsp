<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.menu"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" value="/jsp/menu.jsp" scope="session"/>


<div class="container text-center justify-content-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <c:if test="${empty menu}">
                <fmt:message key="message.emptyMenu" bundle="${rbMsg}"/>
            </c:if>
            <c:if test="${not empty user and !user.isActive()}">
                <span class="text-danger">
                    <fmt:message key="menu.banned"/></span><br>
            </c:if>
            <c:if test="${not empty menu}">
                <ul class="nav justify-content-center text-uppercase">
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#SOUP"><fmt:message key="menu.soups"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#SALAD"><fmt:message key="menu.salads"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#MAIN_DISH"><fmt:message key="menu.mainDishes"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#DESSERT"><fmt:message key="menu.desserts"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#HOT_DRINK"><fmt:message key="menu.hotDrinks"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="#SOFT_DRINK"><fmt:message key="menu.softDrinks"/></a>
                    </li>
                </ul>
                <br>
                <table class="table table-hover table-bordered">
                    <thead class="thead-light text-uppercase">
                    <tr>
                        <c:if test="${role == 'admin'}">
                            <th scope="col">id</th>
                        </c:if>
                        <th scope="col"><fmt:message key="menu.name"/></th>
                        <th scope="col"><fmt:message key="menu.description"/></th>
                        <th scope="col"><fmt:message key="menu.price"/></th>
                        <th scope="col" class="text-nowrap"><fmt:message key="menu.since"/></th>
                        <c:if test="${role == 'customer'}">
                            <th scope="col" class="text-nowrap"><fmt:message key="menu.toCart"/></th>
                        </c:if>
                        <c:if test="${role == 'admin'}">
                            <th scope="col"><fmt:message key="menu.edit"/></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody class="text-left">
                    <c:set var="dishTypePrevious"/>
                    <c:forEach var="dish" items="${menu}" varStatus="status">
                        <c:if test="${dish.type.name() != dishTypePrevious}">
                            <tr class="text-center text-info text-uppercase">
                                <c:choose>
                                    <c:when test="${dish.type.name() == 'SOUP'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.soups"/></th>
                                    </c:when>
                                    <c:when test="${dish.type.name() == 'SALAD'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.salads"/></th>
                                    </c:when>
                                    <c:when test="${dish.type.name() == 'MAIN_DISH'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.mainDishes"/></th>
                                    </c:when>
                                    <c:when test="${dish.type.name() == 'DESSERT'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.desserts"/></th>
                                    </c:when>
                                    <c:when test="${dish.type.name() == 'HOT_DRINK'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.hotDrinks"/></th>
                                    </c:when>
                                    <c:when test="${dish.type.name() == 'SOFT_DRINK'}">
                                        <th scope="row" colspan="6" id="${dish.type.name()}"><fmt:message
                                                key="menu.softDrinks"/></th>
                                    </c:when>
                                </c:choose>
                            </tr>
                            <c:set var="dishTypePrevious" value="${dish.type.name()}"/>
                        </c:if>
                        <tr>
                            <c:if test="${role == 'admin'}">
                                <td scope="row">${dish.dishId}</td>
                            </c:if>
                            <th scope="row">${dish.name}</th>
                            <td>${dish.description}</td>
                            <td>${dish.price}</td>
                            <td><ctg:formatDate date="${dish.createDate}"/></td>
                            <c:if test="${role == 'customer'}">
                                <td class="text-center justify-content-center">
                                    <span class="anchor" id="jumpTag${dish.dishId}"></span>
                                    <form method="post" action="${root}/controller">
                                        <div class="form-row justify-content-center">
                                            <input type="hidden" name="command" value="add_dish_to_cart"/>
                                            <input type="hidden" name="dishId" value="${dish.dishId}">
                                            <input type="number" class="form-control mb-1" name="dishQuantity" min="1"
                                                   max="10" required>
                                            <c:choose>
                                                <c:when test="${user.isActive()}">
                                                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
                                                        <fmt:message key="menu.add"/>
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit"
                                                            disabled><fmt:message key="menu.add"/>
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </form>
                                    <c:if test="${messageDishAddedToCart&& trLabel == dish.dishId}">
                                    <span class="text-success">
                                    <fmt:message key="message.dishAddedToCart" bundle="${rbMsg}"/></span>
                                        <c:remove var="messageDishAddedToCart"/>
                                    </c:if>
                                    <c:if test="${messageInvalidQuantity && trLabel == dish.dishId}">
                                        <fmt:message key="message.invalidQuantity" bundle="${rbMsg}"/><br>
                                        <c:remove var="messageInvalidQuantity"/>
                                    </c:if>
                                </td>
                            </c:if>
                            <c:if test="${role == 'admin'}">
                                <td>
                                    <form target="_blank" method="get" action="${root}/controller">
                                        <input type="hidden" name="command" value="show_edit_dish_form">
                                        <input type="hidden" name="dishToEditId" value="${dish.dishId}">
                                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                                key="menu.edit"/></button>
                                    </form>
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





