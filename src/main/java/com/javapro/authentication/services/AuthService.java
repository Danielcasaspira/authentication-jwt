package com.javapro.authentication.services;

import com.javapro.authentication.common.dtos.TokenResponse;
import com.javapro.authentication.common.dtos.UserRequest;
import org.apache.catalina.User;

public interface AuthService {

    TokenResponse createUser(UserRequest userRequest);

    TokenResponse login(UserRequest userRequest);

}
