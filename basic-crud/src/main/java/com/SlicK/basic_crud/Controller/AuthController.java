package com.SlicK.basic_crud.Controller;

import com.SlicK.basic_crud.Entity.LoginRequestDto;
import com.SlicK.basic_crud.Security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(loginRequestDto.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequestDto.getUsername());
            logger.info("Generated Token: {}", token);
            logger.info("Generated RefreshToken: {}", refreshToken);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header("RefreshAuth", "Refresh " + refreshToken)
                    .body("Token generated successfully");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
