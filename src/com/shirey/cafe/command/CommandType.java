package com.shirey.cafe.command;

import com.shirey.cafe.command.admin.*;
import com.shirey.cafe.command.common.ChangeDateFormatCommand;
import com.shirey.cafe.command.common.ChangeLocaleCommand;
import com.shirey.cafe.command.customer.*;
import com.shirey.cafe.command.admin.ShowEditDishFormCommand;
import com.shirey.cafe.command.dish.ShowMenuCommand;
import com.shirey.cafe.command.dish.ShowOrderDetailsCommand;
import com.shirey.cafe.command.guest.LoginCommand;
import com.shirey.cafe.command.guest.RegisterCommand;
import com.shirey.cafe.command.order.*;
import com.shirey.cafe.command.user.ChangeUserNamesCommand;
import com.shirey.cafe.command.user.ChangeUserPasswordCommand;
import com.shirey.cafe.command.user.ChangeUserPhoneCommand;
import com.shirey.cafe.command.user.LogoutCommand;
import com.shirey.cafe.logic.UserLogic;
import com.shirey.cafe.logic.GuestLogic;
import com.shirey.cafe.logic.CustomerLogic;
import com.shirey.cafe.logic.AdminLogic;
import com.shirey.cafe.logic.DishLogic;
import com.shirey.cafe.logic.OrderLogic;

/**
 * The {@code CommandType} class
 * is enum of all commands.
 * Each type has an {@code Command} object represented by this type.
 *
 * @author Alex Shirey
 */

public enum CommandType {

    //Common
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    CHANGE_DATE_FORMAT(new ChangeDateFormatCommand()),

    //Guest
    LOGIN(new LoginCommand(new GuestLogic())),
    REGISTER(new RegisterCommand(new GuestLogic())),

    //User
    CHANGE_USER_NAMES(new ChangeUserNamesCommand(new UserLogic())),
    CHANGE_USER_PASSWORD(new ChangeUserPasswordCommand(new UserLogic())),
    CHANGE_USER_PHONE(new ChangeUserPhoneCommand(new UserLogic())),
    LOGOUT(new LogoutCommand()),

    //Customer
    ADD_MONEY(new AddMoneyCommand(new CustomerLogic())),
    ADD_DISH_TO_CART(new AddDishToCartCommand(new CustomerLogic())),
    REMOVE_DISH_FROM_CART(new RemoveDishFromCartCommand(new CustomerLogic())),
    SHOW_CART(new ShowCartCommand(new CustomerLogic())),
    SHOW_PLACE_ORDER_PAGE(new ShowPlaceOrderPageCommand(new CustomerLogic())),
    CONFIRM_ORDER(new ConfirmOrderCommand(new CustomerLogic(), new OrderLogic(), new DishLogic())),
    SHOW_CUSTOMER_PAGE(new ShowCustomerPageCommand(new OrderLogic())),
    SHOW_LEAVE_FEEDBACK_PAGE(new ShowLeaveFeedbackPageCommand()),
    LEAVE_FEEDBACK(new LeaveFeedbackCommand(new OrderLogic())),

    //Admin
    SHOW_USERS(new ShowUsersCommand(new AdminLogic())),
    SHOW_DISHES(new ShowDishesCommand(new AdminLogic())),
    SHOW_ORDERS(new ShowOrdersCommand(new AdminLogic(), new UserLogic(), new OrderLogic())),
    SHOW_EDIT_USER_FORM(new ShowEditUserFormCommand(new UserLogic(), new AdminLogic())),
    SHOW_EDIT_DISH_FORM(new ShowEditDishFormCommand(new DishLogic())),
    EDIT_USER(new EditUserCommand(new AdminLogic())),
    EDIT_DISH(new EditDishCommand(new AdminLogic())),
    ADD_DISH(new AddDishCommand(new AdminLogic())),
    REMOVE_REVIEW(new RemoveReviewCommand(new AdminLogic())),

    //Dish
    SHOW_MENU(new ShowMenuCommand(new DishLogic())),
    SHOW_ORDER_DETAILS(new ShowOrderDetailsCommand(new DishLogic())),

    //Order
    CANCEL_ORDER(new CancelOrderCommand(new OrderLogic(), new UserLogic())),
    FINISH_ORDER(new FinishOrderCommand(new OrderLogic())),
    SHOW_REVIEWS(new ShowReviewsCommand(new OrderLogic(), new UserLogic()));

    private Command command;

    /**
     * Sets {@code Command} to the current CommandType
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Returns {@code Command} represented by the current CommandType
     *
     * @return {@code Command} object
     */
    public Command getCommand() {
        return command;
    }

}
