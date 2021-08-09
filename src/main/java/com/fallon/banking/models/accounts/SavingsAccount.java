package com.fallon.banking.models.accounts;

import com.fallon.banking.web.dtos.CreateAccountDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;


@Entity
@Table(name = "savings_accounts")
public class SavingsAccount extends Account{

    @Transient
    private final String type = "savings";

    @Column
    @Min(value = 200)
    private double balance;

    public SavingsAccount() {
    }

    public SavingsAccount(String nickname) {
        super(nickname);
    }

    public SavingsAccount(CreateAccountDTO createAccountDTO) {
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
