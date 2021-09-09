package com.fallon.banking.models.accounts;

import com.fallon.banking.models.AccountRole;
import com.fallon.banking.models.User;
import com.fallon.banking.web.dtos.CreateAccountDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Fetch;

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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private Set<AccountRole> roles;

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


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<AccountRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AccountRole> roles) {
        this.roles = roles;
    }

}
