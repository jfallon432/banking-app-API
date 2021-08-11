package com.fallon.banking.web.controllers;

import com.fallon.banking.models.accounts.Account;
import com.fallon.banking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PutMapping(path = "/deposit/{id}/{amount}")
    public Account deposit(@PathVariable(value = "id") int id, @PathVariable(value = "amount") double ammount){


        return null;
    }



}
