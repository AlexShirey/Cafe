<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@ taglib prefix="ctg" uri="customtags"%>


<html>
<head>
    <title>Online cafe</title>
</head>
<body>

<%-- она нужна для refresh, если refresh не используется, до удалить эту переменную--%>
<c:set var="currentPage" value="/jsp/main.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>
<%--<%@ include file="/jsp/service/header.jsp" %>--%>
<%--<c:set var="isMainPage" value="true"/>--%>

<%--<hr>--%>

<%--<ctg:userInfoTag/>--%>

<%--<hr>--%>


<h3>Welcome, my friend! its a main page!! ${user} (- you should see your info here if you email as user)</h3>
<h2>here you can look for main info ${admin}(- you should see your info here if you email as user)</h2>
<br/>
value from SessionListener - ${test} <br/>
current locale - ${locale} <br/>
id session - ${pageContext.session.id} <br/>
<br/>
<fmt:message key="label.welcome"/> <br/>
<br/>
вывод даты в формате current locale <br/>
<jsp:useBean id="now" class="java.util.Date"/>
short: <fmt:formatDate value="${now}" type="both" timeStyle="short"/> <br/>
full: <fmt:formatDate value="${now}" type="both" timeStyle="full"/> <br/>
default: <fmt:formatDate value="${now}" type="both" timeStyle="default"/> <br/>

ISO pattern yyyy-MM-dd: <fmt:formatDate value="${now}" pattern="yyyy-MM-dd"/><br/>
ISO pattern yyyy-MM-dd HH:mm:ss: <fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/><br/>

вывод времени в формате current locale<br/>
short: <fmt:formatDate value="${now}" type="time" timeStyle="short"/><br/>
medium: <fmt:formatDate value="${now}" type="time" timeStyle="medium"/><br/>
long: <fmt:formatDate value="${now}" type="time" timeStyle="long"/><br/>




receive date:<br/>
short: <fmt:formatDate value="${ldt}" type="both" timeStyle="short"/> <br/>
full: <fmt:formatDate value="${ldt}" type="both" timeStyle="full"/> <br/>
default: <fmt:formatDate value="${ldt}" type="both" timeStyle="default"/> <br/>
ISO pattern yyyy-MM-dd: <fmt:formatDate value="${ldt}" pattern="yyyy-MM-dd"/><br/>
ISO pattern yyyy-MM-dd HH:mm:ss: <fmt:formatDate value="${ldt}" pattern="yyyy-MM-dd HH:mm:ss"/><br/>




<br/>
form input type datetime-local <br/>
<form>
    Birthday (date and time):
    <input type="datetime-local" name="bdaytime">
</form>
<br/>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
