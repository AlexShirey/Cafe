<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Menu</title>
</head>
<body>

<c:set var="currentPage" value="/jsp/menu.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>

<c:if test="${empty menu}">
    <fmt:message key="message.emptyMenu" bundle="${rbMsg}"/>
</c:if>

<c:if test="${not empty user and !user.isActive()}">
    <br>YOU ARE BANNED!!! You can't add dishes to the shopping cart
</c:if>

<c:if test="${not empty menu}">
    <nav>
        <ul>
            <li><a href="#SOUP">SOUPS</a></li>
            <li><a href="#SALAD">SALADS</a></li>
            <li><a href="#MAIN_DISH">MAIN DISHES</a></li>
            <li><a href="#DESSERT">DESSERTS</a></li>
            <li><a href="#HOT_DRINK">HOT DRINKS</a></li>
            <li><a href="#SOFT_DRINK">SOFT DRINKS</a></li>
        </ul>
    </nav>
    <table>
        <thead>
        <tr>
            <c:if test="${role == 'admin'}">
                <th>id</th>
            </c:if>
            <th>name</th>
            <th>description</th>
            <th>price</th>
            <th>in cafe since</th>
            <c:if test="${role == 'customer'}">
                <th>add to cart</th>
            </c:if>
            <c:if test="${role == 'admin'}">
                <th>edit</th>
            </c:if>
        </tr>
        </thead>
        <tbody>

        <c:set var="dishTypePrevious"/>
        <c:forEach var="dish" items="${menu}" varStatus="status">
            <c:if test="${dish.type.name() != dishTypePrevious}">
                <tr class="center">
                    <c:choose>
                        <c:when test="${dish.type.name() == 'SOUP'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">SOUPS</td>
                        </c:when>
                        <c:when test="${dish.type.name() == 'SALAD'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">SALADS</td>
                        </c:when>
                        <c:when test="${dish.type.name() == 'MAIN_DISH'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">MAIN DISHES</td>
                        </c:when>
                        <c:when test="${dish.type.name() == 'DESSERT'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">DESSERTS</td>
                        </c:when>
                        <c:when test="${dish.type.name() == 'HOT_DRINK'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">HOT DRINKS</td>
                        </c:when>
                        <c:when test="${dish.type.name() == 'SOFT_DRINK'}">
                            <td id="${dish.type.name()}" class="center" colspan="6">SOFT DRINKS</td>
                        </c:when>
                    </c:choose>
                </tr>
                <c:set var="dishTypePrevious" value="${dish.type.name()}"/>
            </c:if>
            <tr>
                <c:if test="${role == 'admin'}">
                    <td>${dish.dishId}</td>
                </c:if>
                <td>${dish.name}</td>
                <td>${dish.description}</td>
                <td>${dish.price}</td>
                <td><ctg:formatDate date="${dish.createDate}"/></td>

                <td>
                    <c:if test="${role == 'customer'}">

                        <span class="anchor" id="jumpTag${dish.dishId}"></span>
                        <form method="post" action="${root}/controller">
                            <input type="hidden" name="command" value="add_dish_to_cart"/>
                            <input type="hidden" name="dishId" value="${dish.dishId}">
                            <input type="number" name="dishQuantity" min="1" max="10" required>
                            <c:choose>
                                <c:when test="${user.isActive()}">
                                    <input type="submit" value="add to cart">
                                </c:when>
                                <c:otherwise>
                                    <input type="submit" disabled value="add to cart">
                                </c:otherwise>
                            </c:choose>
                        </form>

                        <c:if test="${messageDishAddedToCart&& trLabel == dish.dishId}">
                            <fmt:message key="message.dishAddedToCart" bundle="${rbMsg}"/><br>
                            <c:remove var="messageDishAddedToCart"/>
                        </c:if>
                        <c:if test="${messageInvalidQuantity && trLabel == dish.dishId}">
                            <fmt:message key="message.invalidQuantity" bundle="${rbMsg}"/><br>
                            <c:remove var="messageInvalidQuantity"/>
                        </c:if>

                    </c:if>

                    <c:if test="${role == 'admin'}">
                        <form target="_blank" method="get" action="${root}/controller">
                            <input type="hidden" name="command" value="show_edit_dish_form">
                            <input type="hidden" name="dishToEditId" value="${dish.dishId}">
                            <input type="submit" value="edit">
                        </form>

                    </c:if>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
