package com.fallon.banking.web.dtos;

import javax.validation.constraints.NotEmpty;

public class LoginUserDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
