<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.profile"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" value="/jsp/user/profile.jsp" scope="session"/>


<div class="container text-center" style="margin-top: 240px">

    <fieldset>
        <legend><fmt:message key="profile.personalInfo"/></legend>
        <fmt:message key="profile.email"/>: ${user.email} <br>
        <fmt:message key="profile.first"/>: ${user.firstName} <br>
        <fmt:message key="profile.last"/>: ${user.lastName} <br>
        <fmt:message key="profile.phone"/>: ${user.phone} <br>
        <br>
        <fmt:message key="profile.blocked"/>: ${!user.active} <br>
        <fmt:message key="profile.registration"/>: <ctg:formatDate date="${user.createDate}"/><br><br>
        <button class="btn btn-outline-warning my-2 my-sm-0" onclick="showChangeProfile()"><fmt:message
                key="profile.btn.changeProfile"/></button>
    </fieldset>
    <div class="row justify-content-center">
        <div class="col-3">
            <fieldset id="changeProfileForm" style="display: none"><br>
                <form method="post" action="${root}/controller">
                    <input type="hidden" name="command" value="change_user_password">
                    <div class="form-group">
                        <input type="password" name="password" class="form-control" id="password" required
                               placeholder="<fmt:message key="profile.newPass.placeholder"/>"
                               autocomplete="off" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}">
                        <small class="form-text text-muted"><fmt:message key="registration.password.small"/>
                        </small>
                    </div>
                    <div class="form-group">
                        <input type="password" id="confirm_password" class="form-control"
                               placeholder="<fmt:message key="profile.confirmPass.placeholder"/>"
                               autocomplete="off" required>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                key="profile.btn.changePass"/></button>
                    </div>
                </form>
                <form autocomplete="on" method="post" action="${root}/controller">
                    <input type="hidden" name="command" value="change_user_names">
                    <div class="form-group">
                        <input type="text" name="first_name" class="form-control" required
                               placeholder="${user.firstName}"
                               pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}">
                        <small class="form-text text-muted"><fmt:message key="registration.first.small"/>
                        </small>
                    </div>
                    <div class="form-group">
                        <input type="text" name="last_name" class="form-control" required placeholder="${user.lastName}"
                               pattern="[A-Z]{1}[a-z]{1,19}|[А-ЯЁ]{1}[а-яё]{1,19}">
                        <small class="form-text text-muted"><fmt:message key="registration.first.small"/>
                        </small>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                key="profile.btn.changeNames"/></button>
                    </div>
                </form>
                <form autocomplete="on" method="post" action="${root}/controller">
                    <input type="hidden" name="command" value="change_user_phone">
                    <div class="form-group">
                        <input type="text" name="phone" class="form-control" required placeholder="${user.phone}"
                               pattern="[+]\d{3}[(]\d{2}[)]\d{3}[-]\d{2}[-]\d{2}">
                        <small class="form-text text-muted"><fmt:message key="registration.phone.small"/></small>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                key="profile.btn.changePhone"/></button>
                    </div>
                </form>
            </fieldset>
        </div>
    </div>
    <c:if test="${messageInvalidInputData}">
        <br><span class="text-center">
            <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/></span>
        <br>
    </c:if>
    <c:if test="${messageProfileChanged}">
        <br><span class="text-success">
        <fmt:message key="message.profileChanged" bundle="${rbMsg}"/></span>
        <c:remove var="messageProfileChanged"/>
        <br>
    </c:if>
    <br>
    <c:if test="${role == 'customer'}">
        <fieldset>
            <legend><fmt:message key="profile.accountInfo"/></legend>
            <fmt:message key="profile.balance"/>: ${user.account.balance} BYN<br>
            <fmt:message key="profile.points"/>: ${user.account.loyaltyPoints} <br><br>
            <form name="addMoney" method="POST" action="${root}/controller">
                <input type="hidden" name="command" value="add_money"/>
                <div class="row justify-content-center">
                    <div class="col-auto">
                        <input type="number" class="form-control" placeholder="100" min="5" max="500" name="moneyAmount"
                               required/>
                    </div>
                    <div class="col-auto">
                        <c:choose>
                            <c:when test="${user.isActive()}">
                                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                                        key="profile.btn.addMoney"/></button>
                            </c:when>
                            <c:otherwise>
                                <button disabled class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                                        key="profile.btn.addMoney"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form>
            <c:if test="${messageInvalidMoneyAmount}">
                <br><span class="text-danger">
                <fmt:message key="message.invalidMoneyAmount" bundle="${rbMsg}"/></span>
            </c:if>
            <c:if test="${messageMoneyAdded}">
                <br><span class="text-success"><c:out value="${moneyAmount}"/>
                <fmt:message key="message.moneyAdded" bundle="${rbMsg}"/></span>
                <c:remove var="messageMoneyAdded"/>
                <c:remove var="moneyAmount"/>
            </c:if>
        </fieldset>
    </c:if>
    <br>
    <hr style="border-color: #1e7e34">
    <p><fmt:message key="profile.selectDate"/></p>
    <div class="row justify-content-center">
        <form name="changeDateFormat" method="get" action="${root}/controller">
            <input type="hidden" name="command" value="change_date_format"/>
            <select class="form-control w-100 mb-10" required name="dateFormatStyle">
                <c:choose>
                    <c:when test="${dateFormatStyle == 'SHORT'}">
                        <option disabled selected id="short"
                                title="short style, e.g 1/24/18 (depends on locale)"
                                value="SHORT">
                            <fmt:message key="profile.dateShort"/>
                        </option>
                        <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)"
                                value="MEDIUM">
                            <fmt:message key="profile.dateMedium"/>
                        </option>
                        <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)"
                                value="LONG">
                            <fmt:message key="profile.dateLong"/>
                        </option>
                        <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO"><fmt:message
                                key="profile.dateISO"/></option>
                    </c:when>
                    <c:when test="${dateFormatStyle == 'MEDIUM'}">
                        <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">
                            <fmt:message key="profile.dateShort"/>
                        </option>
                        <option disabled selected id="medium"
                                title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)"
                                value="MEDIUM">
                            <fmt:message key="profile.dateMedium"/>
                        </option>
                        <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)"
                                value="LONG">
                            <fmt:message key="profile.dateLong"/>
                        </option>
                        <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO"><fmt:message
                                key="profile.dateISO"/></option>
                    </c:when>
                    <c:when test="${dateFormatStyle == 'LONG'}">
                        <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">
                            <fmt:message key="profile.dateShort"/>
                        </option>
                        <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)"
                                value="MEDIUM">
                            <fmt:message key="profile.dateMedium"/>
                        </option>
                        <option disabled selected id="long"
                                title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)"
                                value="LONG"><fmt:message key="profile.dateLong"/>
                        </option>
                        <option title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO"><fmt:message
                                key="profile.dateISO"/></option>
                    </c:when>
                    <c:otherwise>
                        <option id="short" title="short style, e.g 1/24/18 (depends on locale)" value="SHORT">
                            <fmt:message key="profile.dateShort"/>
                        </option>
                        <option id="medium" title="medium style, e.g Jan 24, 2018 10:41 PM (depends on locale)"
                                value="MEDIUM">
                            <fmt:message key="profile.dateMedium"/>
                        </option>
                        <option id="long" title="long type, e.g January 9, 2018 10:41:35 PM (depends on locale)"
                                value="LONG">
                            <fmt:message key="profile.dateLong"/>
                        </option>
                        <option disabled selected title="ISO 8601 style, e.g. 2017-12-31 12:48:55" value="ISO">
                            <fmt:message key="profile.dateISO"/>
                        </option>
                    </c:otherwise>
                </c:choose>
            </select>
            <br>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message
                    key="profile.btn.change"/></button>
        </form>
    </div>
</div>


<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
<script type="text/javascript" src="${root}/js/cafe.js"></script>
</body>











