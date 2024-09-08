package com.javapro.authentication.controllers;


import com.javapro.authentication.common.Constants.ApiPathConstants;
import com.javapro.authentication.common.dtos.TokenResponse;
import com.javapro.authentication.common.dtos.UserRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.AUTH_ROUTE)
public interface AuthApi {

    @PostMapping("/register")
    ResponseEntity<TokenResponse> createUser(@RequestBody @Valid UserRequest userRequest);

    @PostMapping("/login")
    ResponseEntity<TokenResponse> login(@RequestBody UserRequest userRequest);

    @GetMapping
    ResponseEntity<String> getUser(@RequestAttribute(name = "X-user-Id") String userId);

}
