package com.shirey.cafe.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The {@code Order} class
 * is an entity that represents table 'order' in the database.
 * <p>
 * Overrides clone(), equals(), hashcode(), toString() methods.
 *
 * @author Alex Shirey
 */

public class Order extends Entity {

    private int orderId;
    private int userId;
    private PaymentType paymentType;
    private Date pickUpTime;
    private BigDecimal orderPrice;
    private boolean isPaid;
    private Order.Status status;
    private Date createDate;
    private int rating;
    private String review;
    private static final long serialVersionUID = 1L;

    public Order() {
    }

    public Order(int userId, PaymentType paymentType, Date pickUpTime, BigDecimal orderPrice, boolean isPaid) {
        this.userId = userId;
        this.paymentType = paymentType;
        this.pickUpTime = pickUpTime;
        this.orderPrice = orderPrice;
        this.isPaid = isPaid;
    }

    public enum Status {
        ACTIVE, CANCELLED, FINISHED
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        Order copy = (Order) super.clone();
        if (pickUpTime != null) {
            copy.pickUpTime = (Date) pickUpTime.clone();
        }
        if (createDate != null) {
            copy.createDate = (Date) createDate.clone();
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (userId != order.userId) return false;
        if (isPaid != order.isPaid) return false;
        if (rating != order.rating) return false;
        if (paymentType != order.paymentType) return false;
        if (pickUpTime != null ? !pickUpTime.equals(order.pickUpTime) : order.pickUpTime != null) return false;
        if (orderPrice != null ? !orderPrice.equals(order.orderPrice) : order.orderPrice != null) return false;
        if (status != order.status) return false;
        if (createDate != null ? !createDate.equals(order.createDate) : order.createDate != null) return false;
        return review != null ? review.equals(order.review) : order.review == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + userId;
        result = 31 * result + (paymentType != null ? paymentType.hashCode() : 0);
        result = 31 * result + (pickUpTime != null ? pickUpTime.hashCode() : 0);
        result = 31 * result + (orderPrice != null ? orderPrice.hashCode() : 0);
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (review != null ? review.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", paymentType=" + paymentType +
                ", pickUpTime=" + pickUpTime +
                ", orderPrice=" + orderPrice +
                ", isPaid=" + isPaid +
                ", status=" + status +
                ", createDate=" + createDate +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
