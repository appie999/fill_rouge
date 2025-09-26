package com.fillrougeratt.fillrougebackend.mapper;


import com.fillrougeratt.fillrougebackend.dto.response.AuthUserResDTO;
import com.fillrougeratt.fillrougebackend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuthUserResDTO toDTO(User user);
    User toEntity( AuthUserResDTO authUserResDTO);

}
