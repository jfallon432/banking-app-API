package com.fallon.banking.models.accounts;


import com.fallon.banking.web.dtos.CreateAccountDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

@Entity
@Table(name = "checking_accounts")
public class CheckingAccount extends Account{

    @Transient
    private final String type = "checking";

    @Column
    @Min(value = 0)
    private double balance;

    public CheckingAccount() {
    }

    public CheckingAccount(String nickname) {
        super(nickname);
    }

    public CheckingAccount(CreateAccountDTO createAccountDTO) {
        super(createAccountDTO);
        this.balance = createAccountDTO.getStartingBalance();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
