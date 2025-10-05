package com.fillrougeratt.fillrougebackend.service;

import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.mapper.AppointmentMapper;
import com.fillrougeratt.fillrougebackend.model.Appointment;
import com.fillrougeratt.fillrougebackend.model.Doctor;
import com.fillrougeratt.fillrougebackend.model.Patient;
import com.fillrougeratt.fillrougebackend.model.Status;
import com.fillrougeratt.fillrougebackend.repo.AppointmentRepo;
import com.fillrougeratt.fillrougebackend.repo.DoctorRepo;
import com.fillrougeratt.fillrougebackend.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final AppointmentMapper mapper;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;

    public AppointmentService(
            AppointmentRepo appointmentRepo,
            AppointmentMapper mapper, PatientRepo patientRepo, DoctorRepo doctorRepo
    ) {
        this.appointmentRepo = appointmentRepo;
        this.mapper = mapper;
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
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

    public AppointmentDto editAppointment(Long id, AppointmentDto dto){
        Appointment appointment = appointmentRepo.findById(id).get();
        appointment.setId(dto.getId());
        appointment.setDate(dto.getDate());
        appointment.setStatus(Status.valueOf(dto.getStatus()));
        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(()->new RuntimeException("patient not found"));
        appointment.setPatient(patient);
        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(()->new RuntimeException("doctor not found"));
        appointment.setDoctor(doctor);

        return mapper.toDto(appointmentRepo.save(appointment));
    }

    public void deleteAppointment(Long id){
        appointmentRepo.deleteById(id);
    }
}
