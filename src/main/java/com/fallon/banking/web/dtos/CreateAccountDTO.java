package com.fallon.banking.web.dtos;

import javax.validation.constraints.NotEmpty;

public class CreateAccountDTO {
    @NotEmpty
    private String type;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private Double startingBalance;

    public String getNickname() {
        return nickname;
    }

    public Double getStartingBalance() {
        return startingBalance;
    }

    public String getType() {
        return type;
    }
}
