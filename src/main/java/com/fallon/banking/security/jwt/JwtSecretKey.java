package com.fallon.banking.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    @Bean
    public SecretKey secretKey(){
        return Keys.hmacShaKeyFor("revaturerevaturerevaturerevaturerevaturerevaturerevaturerevaturerevaturerevaturerevaturerevaturerevature".getBytes());
    }
}
