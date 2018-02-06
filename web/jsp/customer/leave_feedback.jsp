<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.leaveFeedback"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/customer/leave_feedback.jsp"/>


<div class="container text-center" style="margin-top: 250px">
    <c:if test="${messageFeedbackLeft}">
    <span class="text-success">
        <fmt:message key="message.feedbackLeft" bundle="${rbMsg}"/></span><br><br>
        <hr style="border-color: #1e7e34"><br>
        <form method="get" action="${root}/index.jsp">
            <button class="btn btn-outline-info my-2 my-sm-0" type="submit"><fmt:message key="leaveFeedback.mainPage"/></button>
        </form>
    </c:if>
    <c:if test="${!messageFeedbackLeft}">
        <form name="review" method="post" action="${root}/controller">
            <input type="hidden" name="command" value="leave_feedback"/>
            <input type="hidden" name="orderId" value="${orderId}"/>
            <fieldset>
                <legend><fmt:message key="leaveFeedback.info"/>:</legend><br>
                <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" id="customRadioInline1" name="rating" class="custom-control-input" value="1"
                           required>
                    <label class="custom-control-label" for="customRadioInline1">1 <fmt:message key="leaveFeedback.star"/></label>
                </div>
                <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" id="customRadioInline2" name="rating" class="custom-control-input" value="2"
                           required>
                    <label class="custom-control-label" for="customRadioInline2">2 <fmt:message key="leaveFeedback.stars"/></label>
                </div>
                <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" id="customRadioInline3" name="rating" class="custom-control-input" value="3"
                           required>
                    <label class="custom-control-label" for="customRadioInline3">3 <fmt:message key="leaveFeedback.stars"/></label>
                </div>
                <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" id="customRadioInline4" name="rating" class="custom-control-input" value="4"
                           required>
                    <label class="custom-control-label" for="customRadioInline4">4 <fmt:message key="leaveFeedback.stars"/></label>
                </div>
                <div class="custom-control custom-radio custom-control-inline">
                    <input type="radio" id="customRadioInline5" name="rating" class="custom-control-input" value="5"
                           required>
                    <label class="custom-control-label" for="customRadioInline5">5 <fmt:message key="leaveFeedback.stars5"/></label>
                </div>
                <div class="form-group">
                    <br>
                    <label for="textarea"><fmt:message key="leaveFeedback.review"/></label>
                    <textarea class="form-control" id="textarea" rows="4" placeholder="<fmt:message key="leaveFeedback.review.placeholder"/>"
                              name="review" required></textarea>
                </div><br>
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="customerPage.btn.leaveFeedback"/></button>
            </fieldset>
        </form>
    </c:if>
</div>

<c:remove var="messageFeedbackLeft"/>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>
