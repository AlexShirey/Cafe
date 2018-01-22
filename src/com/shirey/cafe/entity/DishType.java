package com.shirey.cafe.entity;

public enum  DishType {

    SOUP(1), SALAD(2), MAIN_DISH(3), DESSERT(4), HOT_DRINK(5), SOFT_DRINK(6), UNSUPPORTED(0);

    private int dishTypeId;

    DishType(int dishTypeId) {
        this.dishTypeId = dishTypeId;
    }

    public int getDishTypeId() {
        return dishTypeId;
    }
}
