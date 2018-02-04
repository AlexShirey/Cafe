package com.shirey.cafe.entity;

/**
 * The {@code UserRole} class
 * is enum that represents table 'user_role' in the database.
 *
 * @author Alex Shirey
 */

public enum UserRole {

    CUSTOMER(1), ADMIN(2), UNSUPPORTED(0);

    /**
     * Id of the user role in the database.
     */
    private int userRoleId;

    UserRole(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

}
