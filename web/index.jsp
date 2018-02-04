<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="root" value="${pageContext.request.contextPath}" scope="session"/>
<c:import url="/jsp/service/head.jsp"/>

<jsp:forward page="jsp/main.jsp"/>