package com.fillrougeratt.fillrougebackend.mapper;

import com.fillrougeratt.fillrougebackend.dto.UserDto;
import com.fillrougeratt.fillrougebackend.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
    User toEntity(UserDto dto);

}
