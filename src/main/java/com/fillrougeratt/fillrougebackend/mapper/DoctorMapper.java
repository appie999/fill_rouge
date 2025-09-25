package com.fillrougeratt.fillrougebackend.mapper;


import com.fillrougeratt.fillrougebackend.dto.DoctorDto;
import com.fillrougeratt.fillrougebackend.model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDto toDto(Doctor doctor);
    Doctor toEntity(DoctorDto dto);
}
