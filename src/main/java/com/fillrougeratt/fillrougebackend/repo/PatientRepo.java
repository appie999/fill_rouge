package com.fillrougeratt.fillrougebackend.repo;

import com.fillrougeratt.fillrougebackend.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient , Long> {
}
