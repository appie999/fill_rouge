package com.fillrougeratt.fillrougebackend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserResDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String role;

}
