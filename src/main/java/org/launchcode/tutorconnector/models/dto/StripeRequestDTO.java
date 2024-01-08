package org.launchcode.tutorconnector.models.dto;

import jakarta.validation.constraints.*;

public class StripeRequestDTO {

    @NotNull(message = "Amount cannot be null")
    @Min(value = 4, message = "Amount must be atleast 4")
    private static Long amount;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Tutor Session description must include tutor name and session number")
    @Size(min = 5, max = 200, message = "Tutor Session description must be between 5 and 200 characters ")
    private static String tutorSession;

    public StripeRequestDTO () {}

    public StripeRequestDTO(Long amount, String email, String tutorSession) {
        this.amount = amount;
        this.email = email;
        this.tutorSession = tutorSession;
    }

    public static Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getTutorSession() {
        return tutorSession;
    }

    public void setTutorSession(String tutorSession) {
        this.tutorSession = tutorSession;
    }
}
