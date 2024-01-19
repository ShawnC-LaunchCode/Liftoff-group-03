package org.launchcode.tutorconnector.models.dto;

import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import org.launchcode.tutorconnector.models.Subject;

import java.util.ArrayList;
import java.util.List;

public class LoginFormDTO {
//
//    @NotNull(message = "First name is required")
//    @NotBlank(message = "First name is required")
//    private String firstName;
//
//    @NotNull(message = "Last name is required")
//    @NotBlank(message = "Last name is required")
//    private String lastName;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()-+=]).+$", message = "Password must contain at least one special character")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase letter")
    private String password;

//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

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
