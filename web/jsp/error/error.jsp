<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title>Error</title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/error/error.jsp"/>


<div class="container text-center" style="margin-top: 250px">

    <br/>
    Sorry, we couldn't perform your request.
    <br/>
    <hr style="border-color: #1e7e34">
    <br/>
    <a href="${root}/index.jsp">go to main page</a>

</div>


<c:import url="/jsp/service/footer.jsp"/>
</body>

