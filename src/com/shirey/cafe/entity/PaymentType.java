package com.shirey.cafe.entity;

/**
 * The {@code PaymentType} class
 * is enum that represents order payment type.
 *
 * @author Alex Shirey
 */

public enum PaymentType {

    ACCOUNT(10), CASH(5), LOYALTY_POINTS(0);

    /**
     * Percent of loyalty points that user
     * can get for the order, depends on payment type.
     */
    private int loyaltyPointPercent;

    PaymentType(int percent) {
        this.loyaltyPointPercent = percent;
    }

    public int getLoyaltyPointPercent() {
        return loyaltyPointPercent;
    }
}
