package com.shirey.cafe.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The {@code User} class
 * is an entity that represents table 'user' in the database.
 * <p>
 * Overrides clone(), equals(), hashcode(), toString() methods.
 *
 * @author Alex Shirey
 */

public class User extends Entity {

    private int userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Date createDate;
    private Account account;
    private boolean active;
    private UserRole role;
    private static final long serialVersionUID = 1L;

    public User() {
        account = new Account();
    }

    public User(String email, String password, String firstName, String lastName, String phone) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        account = new Account();
    }

    /**
     * Separates the financial fields such as balance and loyalty points
     * from other user's fields.
     * Unites the financial fields to the inner {@code Account} class.
     * <p>
     * Overrides equals(), hashcode(), toString() methods.
     */
    public class Account extends Entity {

        private BigDecimal balance;
        private BigDecimal loyaltyPoints;

        private Account() {
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public BigDecimal getLoyaltyPoints() {
            return loyaltyPoints;
        }

        public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
            this.loyaltyPoints = loyaltyPoints;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Account account = (Account) o;

            if (balance != null ? !balance.equals(account.balance) : account.balance != null) return false;
            return loyaltyPoints != null ? loyaltyPoints.equals(account.loyaltyPoints) : account.loyaltyPoints == null;
        }

        @Override
        public int hashCode() {
            int result = balance != null ? balance.hashCode() : 0;
            result = 31 * result + (loyaltyPoints != null ? loyaltyPoints.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    ", loyaltyPoints=" + loyaltyPoints +
                    '}';
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(int roleId) {
        switch (roleId) {
            case 1:
                this.role = UserRole.CUSTOMER;
                break;
            case 2:
                this.role = UserRole.ADMIN;
                break;
            default:
                this.role = UserRole.UNSUPPORTED;
        }
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User copy = (User) super.clone();
        copy.account = (Account) account.clone();
        if (createDate != null) {
            copy.createDate = (Date) createDate.clone();
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (active != user.active) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (createDate != null ? !createDate.equals(user.createDate) : user.createDate != null) return false;
        if (account != null ? !account.equals(user.account) : user.account != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", createDate=" + createDate +
                ", account=" + account +
                ", active=" + active +
                ", role=" + role +
                '}';
    }
}
