package com.shirey.cafe.entity;

public enum UserRole {

    CUSTOMER(1), ADMIN(2), UNSUPPORTED(0);
    private int userRoleId;

    private UserRole(int userRoleId){
        this.userRoleId = userRoleId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }
}
