package org.launchcode.tutorconnector.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutor extends AbstractEntity {

    private ArrayList<String> qualifications;

    @ManyToMany
    private List<Subject> subjects = new ArrayList<>();

    private String availability;
//
//    private String zoomLink;

    @ManyToMany
    private List<Student> students = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "tutor_id")
    private List<TutorReview> tutorReviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tutor_id")
    private List<Event> events = new ArrayList<>();

    @NotBlank
    @NotNull
    private String pwHash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Tutor() {
    }


    public Tutor(String firstName, String lastName, String email, String password, ArrayList<String> qualifications, List<Subject> subjects, String availability) {
        super(firstName, lastName, email);
        this.qualifications = qualifications;
        this.subjects = subjects;
        this.availability = availability;
        this.pwHash = encoder.encode(password);
    }

    public Tutor(String email, String password) {
        super(email);
        this.pwHash = encoder.encode(password);
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<TutorReview> getTutorReviews() {
        return tutorReviews;
    }

    public void setTutorReviews(List<TutorReview> tutorReviews) {
        this.tutorReviews = tutorReviews;
    }

//    public String getZoomLink() {
//        return zoomLink;
//    }
//
//    public void setZoomLink(String zoomLink) {
//        this.zoomLink = zoomLink;
//    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, this.pwHash);
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "subjects=" + subjects +
                '}';
    }
}

