package com.example.test.user.application.controllers;

import com.example.test.user.application.dto.WithdrawDto;
import com.example.test.user.application.dto.WithdrawRequestBody;
import com.example.test.user.application.services.JwtService;
import com.example.test.user.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// https://spring.io/blog/2019/11/21/spring-security-lambda-dsl

@CrossOrigin(origins = {"*"})
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/protected")
    public String protectedEndpoint(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);

        JwtService.validateAndReadJwt(token);

        return "Hello, world!";
    }

    @PatchMapping("/withdraw-credits/{userId}")
    public int withdrawCredits(@PathVariable UUID userId, @RequestBody WithdrawRequestBody body, @RequestHeader("Authorization") String bearerToken) {
            String token = bearerToken.substring(7);

            JwtService.validateAndReadJwt(token);

            return this.userService.withdrawCredits(new WithdrawDto(userId, body.getCredits()));
        }
}
