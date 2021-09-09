package com.fallon.banking.web.controllers;

import com.fallon.banking.enums.Role;
import com.fallon.banking.models.accounts.Account;
import com.fallon.banking.services.AccountService;
import com.fallon.banking.web.dtos.AccountDTO;
import com.fallon.banking.web.dtos.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping(value = "/create/{id}", consumes = APPLICATION_JSON_VALUE )
    public Account createAccount(@PathVariable(value = "id") int userId, @RequestBody CreateAccountDTO createAccountDTO){
        return accountService.createAccount(createAccountDTO, 1);

    }

    @GetMapping(value = "/get-user-accounts/{id}")
    public List<AccountDTO> getUserAccounts(@PathVariable(value = "id") int userId){

        return accountService.getUserAccounts(userId);
    }





}
