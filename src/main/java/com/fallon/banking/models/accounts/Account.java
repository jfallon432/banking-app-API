package com.fallon.banking.models.accounts;

import com.fallon.banking.models.User;
import com.fallon.banking.web.dtos.CreateAccountDTO;

import javax.persistence.*;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "account_info")
public abstract class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToMany
    @JoinTable(
            name = "users_accounts",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @Column(nullable = false)
    private String type = this.getType();

    @Column
    private String nickname;

    @Transient
    private double balance = this.getBalance();

    public Account(){
    }


    public Account(String nickname) {
    }

    public Account(CreateAccountDTO createAccountDTO){
        this.balance = createAccountDTO.getStartingBalance();
        this.nickname = createAccountDTO.getNickname();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String getType();

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
