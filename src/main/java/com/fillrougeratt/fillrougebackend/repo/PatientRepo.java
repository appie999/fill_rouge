package com.fillrougeratt.fillrougebackend.repo;

import com.fillrougeratt.fillrougebackend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient , Long> {
    Optional<Patient> findByEmail(String email);
}
