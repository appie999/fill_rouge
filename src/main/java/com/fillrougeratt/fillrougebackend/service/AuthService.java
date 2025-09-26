package com.fillrougeratt.fillrougebackend.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public AuthService(
            final UserRepo userRepo,
            final PasswordEncoder encoder,
            UserMapper userMapper
    ) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    public ResponseEntity<AuthUserResDTO> register (RegisterAuthDTO registerAuthDTO) {

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

        // TODO return token
        return new ResponseEntity<>(authUserResDTO, HttpStatus.OK);
    }
}
