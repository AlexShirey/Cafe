package com.shirey.cafe.entity;

/**
 * The {@code DishType} class
 * is enum that represents table 'dish_type' in the database.
 *
 * @author Alex Shirey
 */

public enum DishType {

    SOUP(1), SALAD(2), MAIN_DISH(3), DESSERT(4), HOT_DRINK(5), SOFT_DRINK(6), UNSUPPORTED(0);

    /**
     * Id of the dish type in the database.
     */
    private int dishTypeId;

    DishType(int dishTypeId) {
        this.dishTypeId = dishTypeId;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }
}
