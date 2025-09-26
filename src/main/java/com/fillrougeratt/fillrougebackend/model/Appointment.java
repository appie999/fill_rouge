package com.fillrougeratt.fillrougebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;


}
