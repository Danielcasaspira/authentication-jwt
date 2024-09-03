package com.javapro.authentication.services;

import com.javapro.authentication.common.dtos.TokenResponse;
import io.jsonwebtoken.Claims;

public interface JwtService {

    TokenResponse generateToken(Long userId);

    Claims getClaims(String token);

    boolean isExpired(String token);

    Integer extractUserId(String token);


}
