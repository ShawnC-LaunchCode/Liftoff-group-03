package org.launchcode.tutorconnector.models.dto;

import jakarta.validation.constraints.*;
import org.launchcode.tutorconnector.models.GradeLevel;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Subject;
import org.launchcode.tutorconnector.models.Tutor;

import java.util.ArrayList;
import java.util.List;

public class EditFormDTO {


    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;

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

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8-20 characters")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()-+=]).+$", message = "Password must contain at least one special character")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase letter")
    private static String verifyPassword;

    private List<Subject> subjects;

    private ArrayList<String> qualifications;

    private GradeLevel GradeLevel;


    private String availability;

    public EditFormDTO(){}

    public EditFormDTO(Student student) {
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
    }

    public EditFormDTO(Tutor tutor){
        this.firstName = tutor.getFirstName();
        this.lastName = tutor.getLastName();
        this.email = tutor.getEmail();
        this.qualifications = tutor.getQualifications();
        this.availability = tutor.getAvailability();

    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {this.verifyPassword = verifyPassword;
    }

    public ArrayList<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(ArrayList<String> qualifications) {
        this.qualifications = qualifications;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public org.launchcode.tutorconnector.models.GradeLevel getGradeLevel() {
        return GradeLevel;
    }

    public void setGradeLevel(org.launchcode.tutorconnector.models.GradeLevel gradeLevel) {
        GradeLevel = gradeLevel;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}


