package com.fallon.banking.web.controllers;


import com.fallon.banking.services.UserService;
import com.fallon.banking.web.dtos.AuthenticatedDTO;
import com.fallon.banking.web.dtos.LoginUserDTO;
import com.fallon.banking.web.dtos.RegisterUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody RegisterUserDTO registerUserDTO){

            userService.register(registerUserDTO);

    }

    @PostMapping("/login")
    public AuthenticatedDTO loginUser(@RequestBody LoginUserDTO loginUserDTO){
        return userService.login(loginUserDTO);
    }



}
