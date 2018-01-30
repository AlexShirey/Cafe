<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.editUser"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/admin/edit_user.jsp"/>


<div class="container text-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <c:if test="${userToEdit.userId == user.userId}">
                <span class="text-danger"><fmt:message key="editUser.editYourself"/></span>
            </c:if>
            <c:if test="${messageInvalidQuantity}">
    <span class="text-danger">
    <fmt:message key="message.invalidQuantity" bundle="${rbMsg}"/></span>
            </c:if>
            <c:if test="${messageCantChangeRoleToAdmin}">
    <span class="text-danger">
    <fmt:message key="message.cantChangeRoleToAdmin" bundle="${rbMsg}"/></span>
            </c:if>
            <c:if test="${messageUpdatedSuccessfully}">
    <span class="text-success">
    <fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/></span>
                <br><a href="/controller?command=show_users"><fmt:message key="title.users"/></a>
                <c:remove var="messageUpdatedSuccessfully"/>
            </c:if>
        </div>
    </div>
    <div class="row justify-content-md-center text-left">
        <div class="col-6">
            <form method="post" action="${root}/controller">
                <input type="hidden" name="command" value="edit_user">
                <legend class="text-center"><fmt:message key="editUser.info"/></legend><br>
                <div class="form-group row">
                    <label for="id" class="col col-form-label">Id</label>
                    <div class="col">
                        <input type="text" class="form-control" id="id" placeholder="${userToEdit.userId}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="login" class="col col-form-label"><fmt:message key="registration.login"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="login" placeholder="${userToEdit.email}"
                               disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="first_name" class="col col-form-label"><fmt:message key="registration.first"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="first_name"
                               placeholder="${userToEdit.firstName}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="last_name" class="col col-form-label"><fmt:message key="registration.last"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="last_name" placeholder="${userToEdit.lastName}"
                               disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="phone" class="col col-form-label"><fmt:message key="registration.phone"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="phone" placeholder="${userToEdit.phone}"
                               disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="date" class="col col-form-label"><fmt:message key="editUser.registrationDate"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="date"
                               placeholder="<ctg:formatDate date="${userToEdit.createDate}"/>" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="balance" class="col col-form-label"><fmt:message key="profile.balance"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="balance"
                               placeholder="${userToEdit.account.balance}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="points" class="col col-form-label"><fmt:message key="profile.points"/></label>
                    <div class="col">
                        <input type="number" class="form-control" id="points" required min="0" max="500" step="0.1"
                               name="points"
                               value="${userToEdit.account.loyaltyPoints}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="active" class="col col-form-label"><fmt:message key="users.active"/></label>
                    <div class="col">
                        <select id="active" class="form-control" required name="active">
                            <option selected>${userToEdit.active}</option>
                            <c:if test="${userToEdit.active}">
                                <option value="false">false</option>
                            </c:if>
                            <c:if test="${!userToEdit.active}">
                                <option value="true">true</option>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="role" class="col col-form-label"><fmt:message key="editUser.role"/></label>
                    <div class="col">
                        <select id="role" class="form-control" required name="role">
                            <option selected>${userToEdit.role.name().toLowerCase()}</option>
                            <%--<option selected--%>
                            <%--value="${userToEdit.role.name()}">${userToEdit.role.name().toLowerCase()}</option>--%>
                            <c:forEach var="role" items="${userRoles}">
                                <c:if test="${role != userToEdit.role.name().toLowerCase()}">
                                    <option value="${role}">${role}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row text-center">
                    <div class="col">
                    </div>
                    <div class="col">
                        <c:choose>
                            <c:when test="${userToEdit.userId != user.userId}">
                                <button class="btn btn-sm btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message
                                        key="editUser.btn.update"/></button>
                            </c:when>
                            <c:otherwise>
                                <button disabled class="btn btn-sm btn-outline-danger my-2 my-sm-0" type="submit">
                                    <fmt:message key="editUser.btn.update"/>
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<br>
<br>

<c:import url="/jsp/service/footer.jsp"/>
</body>
