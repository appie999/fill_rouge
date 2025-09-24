package com.fillrougeratt.fillrougebackend.controller;

import com.fillrougeratt.fillrougebackend.dto.request.LoginAuthDTO;
import com.fillrougeratt.fillrougebackend.dto.request.RegisterAuthDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

// add controller annotations
public class AuthController {
    public ResponseEntity<?> register (@Valid @RequestBody RegisterAuthDTO registerAuthDTO) {

        return null;
    }

    public ResponseEntity<?> login (@Valid @RequestBody LoginAuthDTO loginAuthDTO) {

        return null;
    }
}
