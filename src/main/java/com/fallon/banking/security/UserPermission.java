package com.fallon.banking.security;

import com.fallon.banking.services.ApplicationUserService;

public enum UserPermission {
    ACCOUNT_DEPOSIT("account:deposit"),
    ACCOUNT_WITHDRAW("account:withdraw"),
    ACCOUNT_CREATE("account:create");

    private final String permission;

    UserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }

}
