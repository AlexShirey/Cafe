package com.shirey.cafe.entity;


public enum PaymentType {

    ACCOUNT(10), CASH(5), LOYALTY_POINTS(0);

    private int loyaltyPointPercent;

    PaymentType(int percent) {
        this.loyaltyPointPercent = percent;
    }

    public int getLoyaltyPointPercent() {
        return loyaltyPointPercent;
    }
}
