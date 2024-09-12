package com.SlicK.api_gateway.Controller;

import com.SlicK.api_gateway.Security.JwtUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public ResponseEntity<String> auth() {
        String token = jwtUtil.generateToken();
        System.out.println("Generated Token: " + token);
        return ResponseEntity.ok()
                .header("Gateway", "GateTkn " + token)
                .body("Token generated successfully");
    }

    @GetMapping("/limit")
    @RateLimiter(name = "default")
    public ResponseEntity<String> limit(){
        return ResponseEntity.ok("working i guess");
    }
}