package com.fillrougeratt.fillrougebackend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto{

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

}
