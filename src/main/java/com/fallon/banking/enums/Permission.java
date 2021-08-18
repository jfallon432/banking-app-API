package com.fallon.banking.enums;

public enum Permission {
    ACCOUNT_WITHDRAW("account:withdraw"),
    ACCOUNT_CREATE("account:create"),
    ACCOUNT_DEPOSIT("account:deposit"),
    ACCOUNT_ADD_USER("account:add");

    private final String permission;

    Permission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
