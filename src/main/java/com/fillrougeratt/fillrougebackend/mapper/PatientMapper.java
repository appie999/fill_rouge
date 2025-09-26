package com.fillrougeratt.fillrougebackend.mapper;


import com.fillrougeratt.fillrougebackend.dto.PatientDto;
import com.fillrougeratt.fillrougebackend.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDto toDto(Patient patient);
    Patient toEntity(PatientDto dto);
}
