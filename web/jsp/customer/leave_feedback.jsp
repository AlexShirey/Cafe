<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Feedback</title>
</head>
<body>

<c:set var="currentPage" scope="session" value="/jsp/customer/leave_feedback.jsp"/>
<c:import url="/jsp/service/header.jsp"/>


<br>
<c:if test="${messageFeedbackLeft}">
    <br><fmt:message key="message.feedbackLeft" bundle="${rbMsg}"/><br><br><hr>
    <form method="get" action="${root}/index.jsp">
        <input type="submit" value="main page"/>
    </form>
</c:if>

<c:if test="${!messageFeedbackLeft}">
    <form name="review" method="post" action="${root}/controller">
        <input type="hidden" name="command" value="leave_feedback"/>
        <input type="hidden" name="orderId" value="${orderId}"/>
        <fieldset>
            <legend>Please rate and leave feedback:</legend>
            <input type="radio" required id="star5" name="rating" value="5"/>5 stars<br>
            <input type="radio" required id="star4" name="rating" value="4"/>4 stars<br>
            <input type="radio" required id="star3" name="rating" value="3"/>3 stars<br>
            <input type="radio" required id="star2" name="rating" value="2"/>2 stars<br>
            <input type="radio" required id="star1" name="rating" value="1"/>1 star<br>
            <br><br>
            <textarea required rows="4" cols="50" placeholder="enter review here..." name="review"></textarea>
            <br><br>
            <input type="submit" value="leave feedback">
        </fieldset>
    </form>
</c:if>

<c:remove var="messageFeedbackLeft"/>


</body>
</html>
