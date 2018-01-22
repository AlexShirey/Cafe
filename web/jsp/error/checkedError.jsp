<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Checked error page</title>
</head>
<body>

<c:import url="/jsp/service/header.jsp"/>

<br/>
Sorry, we couldn't perform your request...
<br/>
<hr>
message: ${error.getMessage()}
<br/>
<br/>
<a href="${root}/index.jsp">go to main page</a>

<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>
