package com.fillrougeratt.fillrougebackend.service;


import com.fillrougeratt.fillrougebackend.dto.UserDto;
import com.fillrougeratt.fillrougebackend.mapper.UserMapper;
import com.fillrougeratt.fillrougebackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper mapper;


    private UserDto saveUser(UserDto dto){
        return mapper.toDto(userRepo.save(mapper.toEntity(dto)));
    }

    private List<UserDto> getAllUsers(){
        return userRepo.findAll().stream().map(user -> mapper.toDto(user)).toList();
    }

//    private UserDto findUserById(Long id){
//
//    }
}
