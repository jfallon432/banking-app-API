package com.fallon.banking.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.fallon.banking.enums.Permission.*;

public enum Role {
    CUSTODIAN(Sets.newHashSet(ACCOUNT_WITHDRAW, ACCOUNT_DEPOSIT, ACCOUNT_ADD_USER)),
    BENEFICIARY(Sets.newHashSet(ACCOUNT_DEPOSIT)),
    ADULT(Sets.newHashSet(ACCOUNT_CREATE)),
    MINOR(Sets.newHashSet());

    private Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }
}
