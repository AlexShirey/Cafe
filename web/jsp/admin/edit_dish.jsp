<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>


<c:import url="/jsp/service/head.jsp"/>
<head>
    <title><fmt:message key="title.editDish"/></title>
</head>
<body>
<c:import url="/jsp/service/header.jsp"/>
<c:set var="currentPage" scope="session" value="/jsp/admin/edit_dish.jsp"/>


<div class="container text-center" style="margin-top: 250px">
    <div class="row">
        <div class="col">
            <span class="text-danger">
    <c:if test="${messageInvalidInputData}">
        <fmt:message key="message.invalidInputData" bundle="${rbMsg}"/></span>
            </c:if>
            <c:if test="${messageUpdatedSuccessfully}">
    <span class="text-success">
        <fmt:message key="message.updatedSuccessfully" bundle="${rbMsg}"/></span>
                <br><a href="/controller?command=show_dishes"><fmt:message key="title.dishes"/></a>
                <c:remove var="messageUpdatedSuccessfully"/>
            </c:if>
        </div>
    </div>
    <br>
    <div class="row justify-content-md-center text-left">
        <div class="col-6">
            <form method="post" action="${root}/controller">
                <input type="hidden" name="command" value="edit_dish">
                <legend class="text-center"><fmt:message key="title.editDish"/></legend><br>
                <div class="form-group row">
                    <label for="id" class="col col-form-label">Id</label>
                    <div class="col">
                        <input type="text" class="form-control" id="id" placeholder="${dishToEdit.dishId}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="type" class="col col-form-label"><fmt:message key="dishes.type"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="type" placeholder="${dishToEdit.type}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="name" class="col col-form-label"><fmt:message key="dishes.name"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="name" placeholder="${dishToEdit.name}" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="date" class="col col-form-label"><fmt:message key="dishes.createDate"/></label>
                    <div class="col">
                        <input type="text" class="form-control" id="date"
                               placeholder="<ctg:formatDate date="${dishToEdit.createDate}"/>" disabled>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="description" class="col col-form-label"><fmt:message key="dishes.description"/></label>
                    <div class="col">
                        <textarea id="description" class="form-control" id="description" rows="4" cols="50"
                                  name="description">${dishToEdit.description}</textarea>
                        <small><fmt:message key="dishes.description.small"/></small>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="price" class="col col-form-label"><fmt:message key="dishes.price"/></label>
                    <div class="col">
                        <input required type="number" min="0.50" max="100" step="0.1" name="price" class="form-control"
                               id="price" value="${dishToEdit.price}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inMenu" class="col col-form-label"><fmt:message key="dishes.inMenu"/></label>
                    <div class="col">
                        <select id="inMenu" class="form-control" required name="inMenu">
                            <option selected>${dishToEdit.inMenu}</option>
                            <c:if test="${dishToEdit.inMenu}">
                                <option value="false">false</option>
                            </c:if>
                            <c:if test="${!dishToEdit.inMenu}">
                                <option value="true">true</option>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="form-group row text-center">
                    <div class="col">
                    </div>
                    <div class="col">
                        <button class="btn btn-sm btn-outline-danger my-2 my-sm-0" type="submit"><fmt:message key="editUser.btn.update"/></button>
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