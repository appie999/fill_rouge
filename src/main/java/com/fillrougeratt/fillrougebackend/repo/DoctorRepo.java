package com.fillrougeratt.fillrougebackend.repo;

import com.fillrougeratt.fillrougebackend.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {
}
