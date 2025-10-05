package com.fillrougeratt.fillrougebackend.controller;

import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.mapper.AppointmentMapper;
import com.fillrougeratt.fillrougebackend.model.Appointment;
import com.fillrougeratt.fillrougebackend.model.Status;
import com.fillrougeratt.fillrougebackend.repo.AppointmentRepo;
import com.fillrougeratt.fillrougebackend.repo.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctor/appointment")
public class DoctorAppointmentController {

    private final DoctorRepo doctorRepo;



    @Autowired
    private  AppointmentMapper mapper;
    private final AppointmentRepo appointmentRepo;

    public DoctorAppointmentController(DoctorRepo doctorRepo, AppointmentRepo appointmentRepo) {
        this.doctorRepo = doctorRepo;
        this.appointmentRepo = appointmentRepo;
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/verify_relationships")
    public Map<String, Object> verifyDoctorPationRelationships(){
        Map<String, Object> result = new HashMap<>();

        try{
            Long doctorId = currentDoctorId();
            var doctor = doctorRepo.findById(doctorId).orElseThrow(null);

            result.put("doctorId", doctorId);
            result.put("doctorName", doctor != null ? doctor.getFirstName() + " " + doctor.getLastName(): "Not found");
            result.put("doctorEmail", doctor != null ? doctor.getEmail(): "Not found");

            var appointments = appointmentRepo.findByDoctorId(doctorId);
            result.put("totalAppointment", appointments.size());

            long pending = appointments.stream().filter(a -> "ENATTENTE".equals(a.getStatus())).count();
            long approved = appointments.stream().filter(a-> "APPROUVE".equals(a.getStatus())).count();
            long rejected = appointments.stream().filter(a->"REJETE".equals(a.getStatus())).count();

            result.put("pendingCount", pending);
            result.put("approvedCount", approved);
            result.put("rejectedCount", rejected);

            var appointmentDetails = appointments.stream().map(apt->{
                Map<String, Object> aptInfo = new HashMap<>();
                aptInfo.put("id", apt.getId());
                aptInfo.put("date", apt.getDate());
                aptInfo.put("status", apt.getStatus());
                aptInfo.put("doctor", apt.getDoctor());
                aptInfo.put("patient", apt.getPatient());

                if (apt.getPatient() != null){
                    aptInfo.put("patientId", apt.getPatient().getId());
                    aptInfo.put("patientname", apt.getPatient().getFirstName());
                    aptInfo.put("patientemail", apt.getPatient().getEmail());
                }else {
                    aptInfo.put("patientError", "No patient Linked to this appointment !");
                }

                if(apt.getDoctor() != null){
                    aptInfo.put("linkedDoctorId", apt.getDoctor().getId());
                    aptInfo.put("linkedDoctorName", apt.getDoctor().getFirstName() +" " + apt.getDoctor().getLastName());
                }else {
                    aptInfo.put("doctorError","No doctor linked to this appointment !");
                }

                return aptInfo;
            }).toList();

            result.put("appointmentDetails", appointmentDetails);
            result.put("success", true );
        }catch (Exception e){
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    private Long currentDoctorId(){
        String email = AuthUtils.currentUserEmail();

        var doctorOpt = doctorRepo.findByEmail(email);
        if (doctorOpt.isPresent()){
            return doctorOpt.get().getId();
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Doctor introuvable");
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping
    public List<Map<String, Object>> getMyAppointments(){
        Long doctorId = currentDoctorId();
        return appointmentRepo.findById(doctorId)
                .stream()
                .map(a ->{
                    Map<String, Object> appointmentData = new HashMap<>();
                    appointmentData.put("id", a.getId());
                    appointmentData.put("date", a.getDate());
                    appointmentData.put("status", a.getStatus());
                    appointmentData.put("patientId", a.getPatient() != null ? a.getPatient().getId(): null);
                    appointmentData.put("doctorId", a.getDoctor() != null ? a.getDoctor().getId(): null);

                    if (a.getPatient() != null){
                        String patientName = (a.getPatient().getFirstName() != null ? a.getPatient().getFirstName() : ""
                             + " " + (a.getPatient().getLastName() != null ? a.getPatient().getLastName(): ""));
                        appointmentData.put("patientName", patientName.trim());
                        appointmentData.put("patientEmail", a.getPatient().getEmail());
                    }else {
                        appointmentData.put("patientName", "Patient Inconnu");
                        appointmentData.put("patientEmail", "");
                    }
                    return appointmentData;
        } )
        .toList();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/pending")
    public List<Map<String , Object>> getPendingAppointment(){
        Long doctorId = currentDoctorId();

        var pendingAppointments = appointmentRepo.findByDoctorId(doctorId)
                .stream()
                .filter(a->"ENATTENTE".equals(a.getStatus()))
                .toList();

        return pendingAppointments.stream()
                .map(a->{
                    Map<String, Object> appointmentData = new HashMap<>();
                    appointmentData.put("id", a.getId());
                    appointmentData.put("date", a.getDate());
                    appointmentData.put("status", a.getStatus());
                    appointmentData.put("patientId",a.getPatient() != null ? a.getPatient().getId(): null);
                    appointmentData.put("doctorId", a.getDoctor() != null ? a.getDoctor().getId(): null);

                    if (a.getPatient() != null){
                        String patientName = (a.getPatient().getFirstName() != null ? a.getPatient().getFirstName() : ""
                            +" " + (a.getPatient().getLastName() != null ? a.getPatient().getLastName(): ""));
                        appointmentData.put("patientName", patientName.trim());
                        appointmentData.put("patientEmail", a.getPatient().getEmail());
                        appointmentData.put("patientFirstName", a.getPatient().getFirstName());
                        appointmentData.put("patientLastName", a.getPatient().getLastName());
                    }else {
                        appointmentData.put("patientName", "Patient Inconnu");
                        appointmentData.put("patientEmail","");
                    }

                    if (a.getDoctor() != null){
                        appointmentData.put("doctorName",a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName());
                        appointmentData.put("doctorEmail", a.getDoctor().getEmail());
                    }
                    return appointmentData;
                }).toList();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("{id}/approve")
    public AppointmentDto approveAppointment(@PathVariable Long id){
        Long doctorId = currentDoctorId();
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        if (!appointment.getDoctor().getId().equals(doctorId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not your appointment");
        }

        if (!"ENATTENTE".equals(appointment.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment is not pending");
        }

        appointment.setStatus(Status.valueOf("APPROUVE"));
        appointment = appointmentRepo.save(appointment);

        return new  AppointmentDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getStatus(),
                appointment.getPatient() != null ? appointment.getPatient().getId(): null,
                appointment.getDoctor() != null ? appointment.getDoctor().getId(): null
        );
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/{id}/reject")
    public AppointmentDto rejectAppointment(@PathVariable Long id){
        Long doctorId = currentDoctorId();
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        if(! appointment.getDoctor().getId().equals(doctorId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your appointment");
        }

        if (! "ENATTENTE".equals(appointment.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Appointment is not pending");
        }

        appointment.setStatus(Status.valueOf("REJETE"));
        appointment = appointmentRepo.save(appointment);

        return new AppointmentDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getStatus(),
                appointment.getPatient() != null ? appointment.getPatient().getId(): null,
                appointment.getDoctor() != null ? appointment.getDoctor().getId() : null
        );
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/stats")
    public Map<String, Object> getAppointmentStats(){
        Long doctorId = currentDoctorId();
        List<Appointment> allAppointment = appointmentRepo.findByDoctorId(doctorId);

        long totalAppointments = allAppointment.size();
        long pendingAppintments = allAppointment.stream()
                .filter(a->"ENATTENTE".equals(a.getStatus())).count();

        long approvedAppointment = allAppointment.stream()
                .filter(a->"APPROUVE".equals(a.getStatus())).count();

        return Map.of(
                "totalAppointment",totalAppointments,
                "pendingAppointment", pendingAppintments,
                "approvedAppointment",approvedAppointment
        );
    }
}
