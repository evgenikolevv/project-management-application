package com.evgeni.controller;

import com.evgeni.dto.LoginDto;
import com.evgeni.exception.UserNotFoundException;
import com.evgeni.service.interfaces.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping(value = "/login")
    public String login(@Valid @RequestBody LoginDto user) {
        try {

            return loginService.login(user);
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User does not exists.");
        }
    }
}
