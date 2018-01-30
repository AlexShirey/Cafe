<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title>Unchecked error page</title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/error/uncheckedError.jsp"/>


<div class="container text-center te" style="margin-top: 250px">
    <br/>
    <span class="text-danger">Unchecked error!!!</span>
    <br/>
    <hr>
    Sorry, we couldn't perform your request...
    <br/>
    <hr>
    <div class="text-danger">
        <br/>
        Response from ${pageContext.errorData.requestURI} failed
        <br/>
        Status code: ${pageContext.errorData.statusCode}
        <br/>
        exception: ${pageContext.exception}
        <br/>
        caused by: ${pageContext.exception.cause}
        <br/>
    </div>
</div>

<c:import url="/jsp/service/footer.jsp"/>
</body>
