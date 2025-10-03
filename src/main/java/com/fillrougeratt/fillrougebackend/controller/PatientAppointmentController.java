package com.fillrougeratt.fillrougebackend.controller;

import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.dto.BookAppointmentRequest;
import com.fillrougeratt.fillrougebackend.model.Appointment;
import com.fillrougeratt.fillrougebackend.model.Status;
import com.fillrougeratt.fillrougebackend.repo.AppointmentRepo;
import com.fillrougeratt.fillrougebackend.repo.DoctorRepo;
import com.fillrougeratt.fillrougebackend.repo.PatientRepo;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patient/appointments")
public class PatientAppointmentController {

    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;

    public PatientAppointmentController(PatientRepo patientRepo, DoctorRepo doctorRepo, AppointmentRepo appointmentRepo) {
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    private Long currentPatientId(){
        String email = AuthUtils.currentUserEmail();
        return patientRepo.findByEmail(email)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.FORBIDDEN,"Patient introuvable")).getId();
    }

@PreAuthorize("hasRole('PATIENT')")
@PostMapping
    public AppointmentDto bookAppointment(@RequestBody BookAppointmentRequest bookAppointmentRequest){

        if (bookAppointmentRequest.getDoctorId() == null || bookAppointmentRequest.getDate() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"doctorId & date requis");


            if (bookAppointmentRequest.getDate().isBefore(LocalDate.now()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La date doit être >= aujourd'hui");

                var pid = currentPatientId();
                var doctor = doctorRepo.findById(bookAppointmentRequest.getDoctorId())
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Docteur introuvable"));

                var a = new Appointment();
                a.setDate(bookAppointmentRequest.getDate());
                a.setStatus(Status.valueOf("ENATTENTE"));
                a.setPatient(patientRepo.findById(pid).orElseThrow());
                a.setDoctor(doctor);
                a = appointmentRepo.save(a);

                return new AppointmentDto(a.getId(), a.getDate(), a.getStatus(), a.getPatient().getId(), a.getDoctor().getId());

        }

@PreAuthorize("hasRole('PATIENT')")
@GetMapping
    public List<AppointmentDto> myAppointments(){
        var pid = currentPatientId();
        return appointmentRepo.findByPatientIdOrderByDateDesc(pid)
                .stream()
                .map(a->new AppointmentDto(
                        a.getId(),
                        a.getDate(),
                        a.getStatus(),
                        a.getPatient() != null ? a.getPatient().getId(): null,
                        a.getDoctor() != null ? a.getDoctor().getId(): null
                )).toList();
    }

    @PreAuthorize("hasRole('PATIENT')")
    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
        var pid = currentPatientId();
        var a = appointmentRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (a.getPatient() == null || !a.getPatient().getId().equals(pid))
            throw new ResponseStatusException((HttpStatus.FORBIDDEN));
        appointmentRepo.deleteById(id);
    }

}

