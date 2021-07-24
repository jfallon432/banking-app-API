package com.fallon.banking.web.controllers;


import com.fallon.banking.services.UserService;
import com.fallon.banking.web.dtos.RegisterAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterAccountDTO registerAccountDTO ){

            userService.register(registerAccountDTO);

    }

}
