package org.launchcode.tutorconnector.models.dto;

import jakarta.validation.constraints.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StripeRequestDTO {


    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Tutor name must be included.")
    @Size(min = 5, max = 30, message = "Tutor Session name must be between 5 and 30 characters ")
    private String tutorName;

    @NotBlank(message = "Date must be provided")
    private Date sessionDate;

    @Min(value = 1, message = "Duration must be higher than 1")
    private Double sessionDuration;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 4, message = "Amount must be atleast $1usd")
    private Long sessionCost;

    public StripeRequestDTO () {}

    public StripeRequestDTO(String email, String tutorName, Date sessionDate, Double sessionDuration, Long sessionCost) {
        this.email = email;
        this.tutorName = tutorName;
        this.sessionDate = sessionDate;
        this.sessionDuration = sessionDuration;
        this.sessionCost = sessionCost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public @NotBlank(message = "Date must be provided") Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
            this.sessionDate = formatter.parse(dateString);
    }

    public @Min(value = 4, message = "Amount must be atleast 1") Double getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Double sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public Long getSessionCost() {
        return sessionCost;
    }

    public void setSessionCost(Long sessionCost) {
        this.sessionCost = sessionCost;
    }

    @Override
    public String toString() {
        return "StripeRequestDTO{" +
                "sessionDuration=" + sessionDuration +
                '}';
    }
}
