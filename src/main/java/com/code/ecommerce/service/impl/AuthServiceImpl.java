package com.code.ecommerce.service.impl;

import com.code.ecommerce.constance.ResponseStatus;
import com.code.ecommerce.dto.request.LoginRequest;
import com.code.ecommerce.dto.request.RegisterRequest;
import com.code.ecommerce.dto.response.AuthDto;
import com.code.ecommerce.entity.Role;
import com.code.ecommerce.entity.User;
import com.code.ecommerce.exceptions.UserAlreadyExistException;
import com.code.ecommerce.exceptions.UserNotActivatedException;
import com.code.ecommerce.mapper.AddressMapper;
import com.code.ecommerce.mapper.UserMapper;
import com.code.ecommerce.repository.AddressRepository;
import com.code.ecommerce.repository.UserRepository;
import com.code.ecommerce.security.jwt.JwtService;
import com.code.ecommerce.service.AuthService;
import com.code.ecommerce.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${secretPsw}")
    String secretPsw;

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthDto login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User userDetail = (User) authentication.getPrincipal();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", userDetail.getRole());
        extraClaims.put("userId", userDetail.getId());

        String accessToken = jwtService.generateAccessToken(userDetail, extraClaims);
        String refreshToken = jwtService.generateRefreshToken(userDetail);

        //        if (userDetail.getEnabled()) {
        //            throw new APIException("User is not enable!!");
        //        }
        return AuthDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .status(String.valueOf(ResponseStatus.OK))
                .fullName(userDetail.getFirstName() + " " + userDetail.getLastName())
                .type("Bearer")
                .userId(userDetail.getId())
                .role(userDetail.getRole())
                .build();

    }

    @Override
    @Transactional
    public String register(RegisterRequest registerRequest) {
        User savedUser;
        Optional<User> currentUser = userRepository.findByEmail(registerRequest.getEmail());
        if (currentUser.isPresent()) {
            log.error("*** String, service; register user already exists *");
            throw new UserAlreadyExistException("user already exists");
        }


        User user = userMapper.reqToEntity(registerRequest);
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setLocked(true);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        savedUser = userRepository.save(user);


        return savedUser.getId();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = jwtService.getTokenFromRequest(request);
        if (token != null && jwtService.validateToken(token)) {
            String email = jwtService.getEmailFromToken(token);
            if (email != null) {
                User user = userRepository.findByEmail(email).orElseThrow();
                if (jwtService.validateToken(token)) {
                    Map<String, Object> extraClaims = new HashMap<>();
                    extraClaims.put("roles", user.getRole());
                    extraClaims.put("userId", user.getId());
                    String accessToken = jwtService.generateAccessToken(user, extraClaims);
                    AuthDto authDto = AuthDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(token)
                            .status(String.valueOf(ResponseStatus.OK))
                            .fullName(user.getFirstName() + " " + user.getLastName())
                            .type("Bearer")
                            .role(user.getRole())
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authDto);
                }
            }
        }
    }

}
