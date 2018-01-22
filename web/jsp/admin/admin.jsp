<%--<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>

<%--<html>--%>
<%--<head>--%>
    <%--<title>Admin page</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--<c:set var="currentPage" scope="session" value="/jsp/admin/admin.jsp"/>--%>
<%--<c:import url="/jsp/service/header.jsp"/>--%>

<%--<h2>Hi, dear Administrator! here you can browse and manage users, dishes/menu and orders</h2>--%>
<%--<h3>Select what do you want to manage</h3>--%>

<%--<hr>--%>

<%--<ul>--%>
    <%--<li><a href="/controller?command=show_users">users</a></li>--%>
    <%--<li><a href="/controller?command=show_dishes">dishes</a></li>--%>
    <%--<li><a href="/controller?command=show_orders">orders</a></li>--%>

<%--</ul>--%>


<%--&lt;%&ndash;<h3>users</h3>&ndash;%&gt;--%>

<%--&lt;%&ndash;<form method="get" action="${root}/controller">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<input type="hidden" name="command" value="show_users">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<input type="submit" value="get users from database">&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>

<%--&lt;%&ndash;<button onclick="showTableUsers()">hide/show table</button>&ndash;%&gt;--%>

<%--&lt;%&ndash;<c:if test="${not empty users}">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<table class="table-admin" id="tableUsers" style="display: table">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>id</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>login/email</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>first name</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>last name</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>phone</th>&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<th>registration date</th>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>balance</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>loyalty points</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>active</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>role</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>edit</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<tbody>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:forEach var="user" items="${users}" varStatus="status">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.userId}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.email}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.firstName}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.lastName}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.phone}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.account.balance}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.account.loyaltyPoints}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.active}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${user.role} </td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<form method="get" action="${root}/controller">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="hidden" name="command" value="show_edit_user_form">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="hidden" name="UserToEditId" value="${user.userId}">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="submit" value="edit">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</table>&ndash;%&gt;--%>
<%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>


<%--&lt;%&ndash;<hr>&ndash;%&gt;--%>
<%--&lt;%&ndash;<h3>dishes</h3>&ndash;%&gt;--%>

<%--&lt;%&ndash;<form method="get" action="${root}/controller">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<input type="hidden" name="command" value="show_dishes">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<input type="submit" value="get dishes from database">&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>



<%--&lt;%&ndash;<button onclick="showTableDishes()">hide/show table</button>&ndash;%&gt;--%>

<%--&lt;%&ndash;<c:if test="${not empty dishes}">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<table class="table-admin" id="tableDishes" style="display: table">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>id</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>dish type</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>name</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>description</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>price</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>in menu</th>&ndash;%&gt;--%>
                <%--&lt;%&ndash;&lt;%&ndash;<th>createDate</th>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<th>edit</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<tbody>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<c:forEach var="dish" items="${dishes}" varStatus="status">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.dishId}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.type}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.name}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.description}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.price}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>${dish.isInMenu()}</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<form method="get" action="${root}/controller">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="hidden" name="command" value="show_edit_dish_form">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="hidden" name="DishToEditId" value="${dish.dishId}">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="submit" value="edit">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</form>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</tbody>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</table>&ndash;%&gt;--%>
<%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>


<%--&lt;%&ndash;<form action="/action_page.php" method="get">&ndash;%&gt;--%>
<%--&lt;%&ndash;First name: <input type="text" name="fname"><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;Last name: <input type="text" name="lname"><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;<button type="submit">Submit</button><br>&ndash;%&gt;--%>
<%--&lt;%&ndash;<button type="submit" formaction="/action_page2.php">Submit to another page</button>&ndash;%&gt;--%>
<%--&lt;%&ndash;</form>&ndash;%&gt;--%>


<%--<c:import url="/jsp/service/footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>