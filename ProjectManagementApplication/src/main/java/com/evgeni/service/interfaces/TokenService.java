package com.evgeni.service.interfaces;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface TokenService {

    String generateToken(Authentication user);

    Boolean validateToken(String token);

    Long getUserId(String token);

    List<String> getUserRole(String token);
}
