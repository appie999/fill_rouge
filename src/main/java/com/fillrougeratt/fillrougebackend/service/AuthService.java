package com.fillrougeratt.fillrougebackend.service;

import com.fillrougeratt.fillrougebackend.dto.request.LoginAuthDTO;
import com.fillrougeratt.fillrougebackend.dto.request.RegisterAuthDTO;
import com.fillrougeratt.fillrougebackend.dto.response.AuthUserResDTO;
import com.fillrougeratt.fillrougebackend.mapper.UserMapper;
import com.fillrougeratt.fillrougebackend.model.Doctor;
import com.fillrougeratt.fillrougebackend.model.Patient;
import com.fillrougeratt.fillrougebackend.model.Role;
import com.fillrougeratt.fillrougebackend.model.User;
import com.fillrougeratt.fillrougebackend.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public AuthService(
            JwtService jwtService, AuthenticationManager manager,
            final UserRepo userRepo,
            final PasswordEncoder encoder,
            UserMapper userMapper
    ) {
        this.jwtService = jwtService;
        this.manager = manager;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    public ResponseEntity<Map<String, String>> register (RegisterAuthDTO registerAuthDTO) {

        User user = null;

        switch ( Role.valueOf(registerAuthDTO.getRole().toUpperCase()) ) {
            case Role.DOCTOR :
                user = new Doctor();
                break;
            case Role.PATIENT:
                user = new Patient();
                break;
        }

        user.setFirstName( registerAuthDTO.getFirstName() );
        user.setLastName(registerAuthDTO.getLastName());
        user.setUserName(registerAuthDTO.getUserName());
        user.setEmail(registerAuthDTO.getEmail());
        user.setPassword(encoder.encode(registerAuthDTO.getPassword()) );
        user.setRole(Role.valueOf(registerAuthDTO.getRole().toUpperCase()));

        if (user instanceof Doctor doctor) {
            doctor.setSpecialty(registerAuthDTO.getSpecialty());
        }

        User savedUser = userRepo.save( user );

        AuthUserResDTO authUserResDTO = userMapper.toDTO( savedUser );

        String genToken = jwtService.generateToken(authUserResDTO);
        Map<String, String> token = new HashMap<>();
        token.put("token", genToken);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    public ResponseEntity<Map<String, String>> login(LoginAuthDTO loginAuthDTO){

        if(loginAuthDTO.getEmail() == null || loginAuthDTO.getEmail().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is requered");
        }

        if(loginAuthDTO.getPassword() == null || loginAuthDTO.getPassword().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password is required");
        }

        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginAuthDTO.getEmail().trim(), loginAuthDTO.getPassword())
            );

            User user = userRepo.findByEmail(loginAuthDTO.getEmail().trim())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

            AuthUserResDTO authUserResDTO = userMapper.toDTO( user );

            String genToken = jwtService.generateToken(authUserResDTO);
            Map<String, String> token = new HashMap<>();
            token.put("token", genToken);
            return ResponseEntity.ok(token);
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        }

    }
}
