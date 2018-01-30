package com.shirey.cafe.command.admin;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.Order;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.logic.OrderLogic;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.manager.PageManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ShowOrdersCommand implements Command {

    private static final String PAGE_ORDERS = "page.orders";
    private static final String PARAM_USER_ID_TO_SHOW_ORDERS = "userIdToShowOrders";
    private AdminLogic adminLogic;
    private UserLogic userLogic;
    private OrderLogic orderLogic;


    public ShowOrdersCommand(AdminLogic adminLogic, UserLogic userLogic, OrderLogic orderLogic) {
        this.adminLogic = adminLogic;
        this.userLogic = userLogic;
        this.orderLogic = orderLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        List<Order> orders;
        String userIdToShowOrders = request.getParameter(PARAM_USER_ID_TO_SHOW_ORDERS);

        if (userIdToShowOrders != null) {
            int userId = Integer.parseInt(userIdToShowOrders);
            orders = orderLogic.findUserOrders(userId);
            User userToShowOrders = userLogic.findUserById(userId);
            request.getSession().setAttribute("userToShowOrders", userToShowOrders);
        } else {
            orders = adminLogic.findAllOrders();
            List<User> usersWithOrders = userLogic.findUsersWithOrders();
            request.getSession().setAttribute("usersWithOrders", usersWithOrders);
            request.getSession().removeAttribute("userToShowOrders");
        }

        request.getSession().setAttribute("orders", orders);

        Router router = new Router();
        router.setPage(PageManager.getProperty(PAGE_ORDERS));

        return router;

    }
}
