package com.code.ecommerce.controller;


import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.LoginRequest;
import com.code.ecommerce.dto.request.RegisterRequest;
import com.code.ecommerce.dto.response.ResponseMessage;
import com.code.ecommerce.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:3010")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("*** UserDto List, controller; login *");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage(ResponseStatus.OK, "Login successful !!!", authService.login(loginRequest)) );
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Validated RegisterRequest registerRequest) {
        log.info("*** UserDto List, controller; register *");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseMessage(ResponseStatus.OK, "Register successful !!!",authService.register(registerRequest)));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
