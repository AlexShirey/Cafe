package com.shirey.cafe.command.customer;

import com.shirey.cafe.command.Command;
import com.shirey.cafe.controller.Router;
import com.shirey.cafe.entity.User;
import com.shirey.cafe.exception.LogicException;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.manager.PageManager;
import com.shirey.cafe.util.InputDataValidator;

import javax.servlet.http.HttpServletRequest;


public class AddMoneyCommand implements Command {

    private static final String PAGE_PROFILE = "page.profile";
    private static final String PARAM_MONEY_AMOUNT = "moneyAmount";
    private CustomerLogic customerLogic;

    public AddMoneyCommand(CustomerLogic customerLogic) {
        this.customerLogic = customerLogic;
    }

    @Override
    public Router execute(HttpServletRequest request) throws LogicException {

        String amount = request.getParameter(PARAM_MONEY_AMOUNT);

        User user = (User) request.getSession().getAttribute("user");

        if (!InputDataValidator.isPositiveNumber(amount)) {
            request.setAttribute("messageInvalidMoneyAmount", true);
            return refreshForward(PageManager.getProperty(PAGE_PROFILE));
        }

        customerLogic.addMoney(user, amount);

        request.getSession().setAttribute("moneyAmount", amount);
        request.getSession().setAttribute("messageMoneyAdded", true);

        return refreshRedirect(PageManager.getProperty(PAGE_PROFILE));
    }
}

