package com.fillrougeratt.fillrougebackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor extends User {
    private String specialty;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
}
