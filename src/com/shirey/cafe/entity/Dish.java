package com.shirey.cafe.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The {@code Dish} class
 * is an entity that represents table 'dish' in the database.
 * <p>
 * Overrides clone(), equals(), hashcode(), toString() methods.
 *
 * @author Alex Shirey
 */

public class Dish extends Entity {

    private int dishId;
    private DishType type;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean inMenu;
    private Date createDate;
    private static final long serialVersionUID = 1L;

    public Dish() {
    }

    public Dish(DishType type, String name, String description, BigDecimal price, boolean inMenu) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inMenu = inMenu;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public DishType getType() {
        return type;
    }

    public void setType(int typeId) {

        switch (typeId) {
            case 1:
                this.type = DishType.SOUP;
                break;
            case 2:
                this.type = DishType.SALAD;
                break;
            case 3:
                this.type = DishType.MAIN_DISH;
                break;
            case 4:
                this.type = DishType.DESSERT;
                break;
            case 5:
                this.type = DishType.HOT_DRINK;
                break;
            case 6:
                this.type = DishType.SOFT_DRINK;
                break;
            default:
                this.type = DishType.UNSUPPORTED;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public void setInMenu(boolean inMenu) {
        this.inMenu = inMenu;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Dish clone() throws CloneNotSupportedException {
        Dish copy = (Dish) super.clone();
        if (createDate != null) {
            copy.createDate = (Date) createDate.clone();
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (dishId != dish.dishId) return false;
        if (inMenu != dish.inMenu) return false;
        if (type != dish.type) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        return createDate != null ? createDate.equals(dish.createDate) : dish.createDate == null;
    }

    @Override
    public int hashCode() {
        int result = dishId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (inMenu ? 1 : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", inMenu=" + inMenu +
                ", createDate=" + createDate +
                '}';
    }
}
