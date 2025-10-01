package com.fillrougeratt.fillrougebackend.service;

import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.mapper.AppointmentMapper;
import com.fillrougeratt.fillrougebackend.model.Appointment;
import com.fillrougeratt.fillrougebackend.model.Status;
import com.fillrougeratt.fillrougebackend.repo.AppointmentRepo;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper mapper;
    private final ResourcePatternResolver resourcePatternResolver;

    public AppointmentService(AppointmentRepo appointmentRepo, AppointmentMapper mapper, ResourcePatternResolver resourcePatternResolver) {
        this.appointmentRepo = appointmentRepo;
        this.mapper = mapper;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public AppointmentDto saveAppointment(AppointmentDto dto){
        return mapper.toDto(appointmentRepo.save(mapper.toEntity(dto)));
    }

    public AppointmentDto getAppointmentById(Long id){
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->new RuntimeException("Appointment not found"));
        return mapper.toDto(appointment);
    }

    public List<AppointmentDto> getAllAppointment(){
        return appointmentRepo.findAll().stream().map(appointment -> mapper.toDto(appointment)).toList();
    }

//    public AppointmentDto editAppointment(Long id, AppointmentDto dto){
//        Appointment appointment = appointmentRepo.findById(id).get();
//        appointment.setDate(dto.getDate());
//        appointment.setStatus(Status.valueOf(dto.getStatus()));
//        appointment.setDoctor(dto.getDoctorName());
//    }

    public void deleteAppointment(Long id){
        appointmentRepo.deleteById(id);
    }
}
