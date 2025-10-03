package com.fillrougeratt.fillrougebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAppointmentRequest {

    private Long doctorId;
    private LocalDate date;

}
