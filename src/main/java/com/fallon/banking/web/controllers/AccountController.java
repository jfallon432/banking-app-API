package com.fallon.banking.web.controllers;

import com.fallon.banking.models.accounts.Account;
import com.fallon.banking.services.AccountService;
import com.fallon.banking.web.dtos.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE )
    public Account createAccount(@RequestBody CreateAccountDTO createAccountDTO, HttpServletRequest req){
        int userId = 1;
        return accountService.createAccount(createAccountDTO, userId);

    }


}
