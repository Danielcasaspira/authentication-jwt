package com.javapro.authentication.services;

import com.javapro.authentication.common.dtos.TokenResponse;
import com.javapro.authentication.common.dtos.UserRequest;

public interface AuthService {

    TokenResponse createUser(UserRequest userRequest);

}
