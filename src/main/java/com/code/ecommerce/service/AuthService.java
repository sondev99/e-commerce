package com.code.ecommerce.service;


import com.code.ecommerce.dto.request.LoginRequest;
import com.code.ecommerce.dto.request.RegisterRequest;
import com.code.ecommerce.dto.response.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {

    AuthDto login(LoginRequest categoryRequest);

    String register(RegisterRequest registerRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
