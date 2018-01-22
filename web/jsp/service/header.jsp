<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:set var="root" value="${pageContext.request.contextPath}" scope="session"/>


<link href="https://fonts.googleapis.com/css?family=Lato: 100,300,400,700|Luckiest+Guy|Oxygen:300,400"
      rel="stylesheet">
<link href="${root}/css/cafe.css?version=1.234234234" type="text/css" rel="stylesheet">

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" scope="session"/>
<fmt:setBundle basename="resources.configuration.messages" scope="session" var="rbMsg"/>

<fmt:message key="btn.home" var="btnHome"/>


<header class="navigation">

    <div>
        <span><ctg:userInfo/></span>
    </div>
    <%--<ctg:dateFormatTag date="${Date()}"/>--%>
    <%--<div>--%>
    <%--<c:choose>--%>
    <%--<c:when test="${isLogin}">--%>
    <%--<span>You logged in as ${role}, your name is ${user.firstName}</span>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--<span>You are not logged in, guest. Please, log in</span>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</div>--%>

    <ul>
        <c:choose>
            <c:when test="${isLoginPage}">
                <li><a href="${root}/index.jsp">${btnHome}</a></li>
                <li><a href="${root}/jsp/guest/registration.jsp">REGISTRATION</a></li>
            </c:when>
            <c:when test="${isRegistrationPage}">
            </c:when>
            <c:otherwise>
                <li><a href="${root}/index.jsp">${btnHome}</a></li>
                <li><a href="${root}/controller?command=show_menu">MENU</a></li>
                <%--<li><a href="${root}/jsp/reviews.jsp">REVIEWS</a></li>--%>
                <li><a href="${root}/controller?command=show_reviews">REVIEWS</a></li>
                <c:choose>
                    <c:when test="${isLogin}">
                        <c:if test="${role == 'customer'}">
                            <li><a href="${root}/controller?command=show_cart">SHOPPING CART</a></li>
                            <li><a href="${root}/controller?command=show_customer_page">CUSTOMER PAGE</a></li>
                            <%--<li><a href="${root}/jsp/user/profile.jsp">PROFILE</a></li>--%>
                        </c:if>
                        <%--<c:if test="${role == 'admin'}">--%>
                            <%--<li><a href="${root}/jsp/admin/admin.jsp">ADMIN PAGE</a></li>--%>
                            <%--&lt;%&ndash;<li><a href="${root}/jsp/admin/profile.jsp">PROFILE</a></li>&ndash;%&gt;--%>
                        <%--</c:if>--%>
                        <%--<c:choose>--%>
                        <%--<c:when test="${role == 'customer'}">--%>
                        <%--<li><a href="${root}/jsp/customer/cart.jsp">SHOPPING CART</a></li>--%>
                        <%--</c:when>--%>
                        <%--</c:choose>--%>
                        <li><a href="${root}/jsp/user/profile.jsp">PROFILE</a></li>
                        <li><a href="${root}/controller?command=logout">LOGOUT</a></li>
                    </c:when>
                    <c:otherwise>
                        <%--<li><a href="${root}/jsp/guest/login.jsp">SHOPPING CART</a></li>--%>
                        <li><a href="${root}/jsp/guest/login.jsp">LOGIN</a></li>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>

        <li>
            <form name="changeLocale" method="POST" action="${root}/controller">
                <input type="hidden" name="command" value="change_locale"/>
                <select name="locale">
                    <c:choose>
                        <c:when test="${locale == 'en_US'}">
                            <option disabled selected title="english, current language" value="en_US">en</option>
                            <option value="ru_RU" title="русский">ru</option>
                        </c:when>
                        <c:otherwise>
                            <option value="en_US" title="english">en</option>
                            <option disabled selected title="русский, текущий язый" value="ru_RU">ru</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <input type="submit" value="change"/>
            </form>
        </li>
    </ul>
    <c:if test="${role == 'admin'}">
        <%--<br>--%>
        <%--<ul>--%>
            <li class="li-null-padding"><a href="/controller?command=show_users">users</a></li>
            <li class="li-null-padding"><a href="/controller?command=show_dishes">dishes</a></li>
            <li class="li-null-padding"><a href="/controller?command=show_orders">orders</a></li>


        <%--</ul>--%>


        <%--<li><a href="${root}/jsp/admin/admin.jsp">ADMIN PAGE</a></li>--%>
        <%--<li><a href="${root}/jsp/admin/profile.jsp">PROFILE</a></li>--%>
    </c:if>






</header>
