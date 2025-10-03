package com.fillrougeratt.fillrougebackend.repo;

import com.fillrougeratt.fillrougebackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Long> {

    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByPatientIdOrderByDateDesc(Long patientId);

}
