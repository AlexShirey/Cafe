<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" scope="session"/>
<fmt:setBundle basename="resources.configuration.messages" scope="session" var="rbMsg"/>

<%--<c:set var="root" value="${pageContext.request.contextPath}" scope="session"/>--%>

<div class="fixed-top"
     style="background-image: url(${root}/img/header.jpeg); max-width: 100%; height: auto; ">
    <div class="jumbotron bg-transparent"
         style="margin-bottom: 0; padding: 30px;">
        <div class="container text-center">
            <h1>Online Cafe</h1>
            <p>Tasty food & drinks</p>
        </div>
    </div>
    <nav class="navbar navbar-expand-lg navbar-light bg-transparent">
        <a class="navbar-brand text-info" href="${root}/index.jsp"><fmt:message key="nav.home"/></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto nav nav-tabs" style="border-color: #1e7e34">
                <c:choose>
                    <c:when test="${isLoginPage}">
                    </c:when>
                    <c:when test="${isRegistrationPage}">
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link font-weight-bold"
                               href="${root}/controller?command=show_menu"><fmt:message key="nav.menu"/></a>
                        </li>
                        <c:if test="${role != 'customer'}">
                            <li class="nav-item">
                                <a class="nav-link font-weight-bold"
                                   href="${root}/controller?command=show_reviews"><fmt:message key="nav.reviews"/></a>
                            </li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <c:if test="${role == 'customer'}">
                    <li class="nav-item">
                        <a class="nav-link font-weight-bold" href="${root}/controller?command=show_cart"><fmt:message
                                key="nav.cart"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-weight-bold"
                           href="${root}/controller?command=show_customer_page"><fmt:message key="nav.customer"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link font-weight-bold"
                           href="${root}/controller?command=show_reviews"><fmt:message key="nav.reviews"/></a>
                    </li>
                </c:if>
                <c:if test="${role == 'admin'}">
                    <li class="nav-item dropdown font-weight-bold">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown"
                           aria-haspopup="true" aria-expanded="false"><fmt:message key="nav.database"/></a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item"
                               href="${root}/controller?command=show_users"><fmt:message key="nav.users"/></a>
                            <a class="dropdown-item"
                               href="${root}/controller?command=show_dishes"><fmt:message key="nav.dishes"/></a>
                            <a class="dropdown-item"
                               href="${root}/controller?command=show_orders"><fmt:message key="nav.orders"/></a>
                        </div>
                    </li>
                </c:if>
            </ul>
            <div class="form-inline my-2 my-lg-0">
                <ul class="navbar-nav mr-auto nav nav-tabs" style="border-color: #1e7e34">
                    <c:choose>
                        <c:when test="${isLogin}">
                            <li class="nav-item">
                                <a class="nav-link font-weight-bold" href="${root}/jsp/user/profile.jsp"><fmt:message
                                        key="nav.profile"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link font-weight-bold"
                                   href="${root}/controller?command=logout"><fmt:message key="nav.logout"/></a>
                            </li>
                        </c:when>
                        <c:when test="${isLoginPage}">
                            <li class="nav-item">
                                <a class="nav-link font-weight-bold"
                                   href="${root}/jsp/guest/registration.jsp"><fmt:message key="nav.registration"/></a>
                            </li>
                        </c:when>
                        <c:when test="${isRegistrationPage}">
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link font-weight-bold" href="${root}/jsp/guest/login.jsp"><fmt:message
                                        key="nav.login"/></a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <form name="changeLocale" method="POST" action="${root}/controller">
                    <input type="hidden" name="command" value="change_locale"/>
                    <select class="form-control ml-2 form-control-sm" name="locale" style="margin-right: 10px">
                        <c:choose>
                            <c:when test="${locale == 'en_US'}">
                                <option disabled selected title="english, current language" value="en_US">en</option>
                                <option value="ru_RU" title="русский">ру</option>
                            </c:when>
                            <c:otherwise>
                                <option value="en_US" title="english">en</option>
                                <option disabled selected title="русский, текущий язый" value="ru_RU">ру</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                    <button class="btn btn-sm btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                            key="nav.change"/></button>
                </form>
            </div>
        </div>
    </nav>
</div>