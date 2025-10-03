package com.fillrougeratt.fillrougebackend.controller;

import com.fillrougeratt.fillrougebackend.dto.request.LoginAuthDTO;
import com.fillrougeratt.fillrougebackend.dto.request.RegisterAuthDTO;
import com.fillrougeratt.fillrougebackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    // @Valid
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterAuthDTO registerAuthDTO) {
        return authService.register( registerAuthDTO );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginAuthDTO loginAuthDTO) {
        return authService.login(loginAuthDTO);
    }

}
