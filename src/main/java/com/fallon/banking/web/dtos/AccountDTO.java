package com.fallon.banking.web.dtos;

import com.fallon.banking.enums.Role;
import com.fallon.banking.models.accounts.Account;

public class AccountDTO {
    private Role role;
    private int id;
    private String type;
    private String nickname;
    private double balance;

    public AccountDTO(Account account){
        this.id = account.getId();
        this.balance = account.getBalance();
        this.nickname = account.getNickname();
        this.type = account.getType();
    }

    public AccountDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
