package com.fillrougeratt.fillrougebackend.mapper;


import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.model.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto toDto(Appointment appointment);
    Appointment toEntity(AppointmentDto dto);

}
