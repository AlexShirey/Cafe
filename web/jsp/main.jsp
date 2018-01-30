<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" scope="session"/>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.main"/></title>
</head>

<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" value="/jsp/main.jsp" scope="session"/>

<div class="container text-center"
     style="margin-top: 250px">

    <ctg:userInfo/>
    <hr style="border-color: #1e7e34">
    <br>

    <fmt:message key="main.info"/>

</div>

<c:import url="/jsp/service/footer.jsp"/>
</body>
