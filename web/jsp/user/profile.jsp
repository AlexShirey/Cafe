<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>Profile</title>
</head>
<body>

<c:set var="currentPage" value="/jsp/user/profile.jsp" scope="session"/>
<c:import url="/jsp/service/header.jsp"/>

<br>
<fieldset>
    <legend>Personal information:</legend>
    Email: ${user.email} <br>
    First name: ${user.firstName} <br>
    Last name: ${user.lastName} <br>
    Phone: ${user.phone} <br> <br>
    <button onclick="showChangeProfile()">change profile info</button>
    <br><br>
    You are blocked: ${!user.active} <br>
    Registration date&time: <ctg:formatDate date="${user.createDate}"/>
</fieldset>

<fieldset id="changeProfileForm" style="display: none">
    <legend>Change personal information:</legend>
    <form method="post" action="${root}/controller">
        <input type="hidden" name="command" value="change_user_password">
        <input type="password" name="password" id="password" required placeholder="new password"
               autocomplete="off" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}"><br>
        <small>6+ chars (at least one number, one upper and lower case letter, max 30 chars)</small>
        <br>
        <input type="password" id="confirm_password" placeholder="confirm password" autocomplete="off"><br>
        <input type="submit" value="change password">
    </form>
    <form autocomplete="on" method="post" action="${root}/controller">
        <input type="hidden" name="command" value="change_user_names">
        <input type="text" name="first_name" required placeholder="${user.firstName}"
               pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}">
        <br>
        <small>2+ chars (letters only, first letter is Capital, max 20 chars (RU or EN))</small>
        <br>
        <input type="text" name="last_name" required placeholder="${user.lastName}"
               pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}">
        <br>
        <small>2+ chars (letters only, first letter is Capital, max 20 chars (RU or EN))</small>
        <br>
        <input type="submit" value="change names">
    </form>
    <form autocomplete="on" method="post" action="${root}/controller">
        <input type="hidden" name="command" value="change_user_phone">
        <input type="text" name="phone" required placeholder="${user.phone}"
               pattern="[+]\d{3}[(]\d{2}[)]\d{3}[-]\d{2}[-]\d{2}">
        <br>
        <small>BY format +375(29)612-61-09</small>
        <br>
        <input type="submit" value="change phone">
    </form>
</fieldset>

<c:if test="${messageInvalidInputData}">
    <br>
    <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/>
    <br>
</c:if>
<c:if test="${messageProfileChanged}">
    <br>
    <fmt:message key="message.profileChanged" bundle="${rbMsg}"/>
    <c:remove var="messageProfileChanged"/>
    <br>
</c:if>

<br>
<c:if test="${role == 'customer'}">
    <fieldset>
        <legend>Account information:</legend>
        Balance: ${user.account.balance} BYN<br>
        <form name="addMoney" method="POST" action="${root}/controller">
            <input type="hidden" name="command" value="add_money"/>
            <input type="number" placeholder="100" min="5" max="500" name="moneyAmount" required/>
            <c:choose>
                <c:when test="${user.isActive()}">
                    <input type="submit" value="add money"/>
                </c:when>
                <c:otherwise>
                    <input type="submit" disabled value="add money">
                </c:otherwise>
            </c:choose>
        </form>
        <c:if test="${messageInvalidMoneyAmount}">
            <fmt:message key="message.invalidMoneyAmount" bundle="${rbMsg}"/><br><br>
        </c:if>
        <c:if test="${messageMoneyAdded}">
            <c:out value="${moneyAmount}"/>
            <fmt:message key="message.moneyAdded" bundle="${rbMsg}"/><br><br>
            <c:remove var="messageMoneyAdded"/>
            <c:remove var="moneyAmount"/>
        </c:if>
        Loyalty Points: ${user.account.loyaltyPoints} <br>
    </fieldset>
</c:if>


<p> You can select date-time style to be shown: </p>
<form name="changeDateFormat" method="get" action="${root}/controller">
    <input type="hidden" name="command" value="change_date_format"/>
    <select name="dateFormatStyle">
        <c:choose>
            <c:when test="${dateFormatStyle == 'SHORT'}">
                <option disabled selected id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">
                    short
                </option>
                <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)" value="MEDIUM">
                    medium
                </option>
                <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)" value="LONG">
                    long
                </option>
                <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO">ISO</option>
            </c:when>
            <c:when test="${dateFormatStyle == 'MEDIUM'}">
                <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">short</option>
                <option disabled selected id="medium"
                        title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)" value="MEDIUM">medium
                </option>
                <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)" value="LONG">
                    long
                </option>
                <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO">ISO</option>
            </c:when>
            <c:when test="${dateFormatStyle == 'LONG'}">
                <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">short</option>
                <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)" value="MEDIUM">
                    medium
                </option>
                <option disabled selected id="long"
                        title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)" value="LONG">long
                </option>
                <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO">ISO</option>
            </c:when>
            <c:otherwise>
                <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">short</option>
                <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)" value="MEDIUM">
                    medium
                </option>
                <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)" value="LONG">
                    long
                </option>
                <option disabled selected title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO">ISO</option>
            </c:otherwise>
        </c:choose>
    </select>
    <input type="submit" value="change"/>
</form>


<c:import url="/jsp/service/footer.jsp"/>
</body>
</html>










