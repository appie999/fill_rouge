package com.fillrougeratt.fillrougebackend.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class LoginAuthDTO {

    private String email;

    private String password;

    public LoginAuthDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginAuthDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
