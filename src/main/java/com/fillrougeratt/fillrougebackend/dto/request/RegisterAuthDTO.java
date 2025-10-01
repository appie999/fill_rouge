package com.fillrougeratt.fillrougebackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAuthDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private String role;
    private String specialty;

    public RegisterAuthDTO(String token, String name, String userName, Long id) {
    }
}
