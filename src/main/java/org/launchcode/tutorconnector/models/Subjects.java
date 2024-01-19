package org.launchcode.tutorconnector.models;

import jakarta.persistence.*;

@Entity
public enum Subjects {

    MATH("Math",1),
    ENGLISH("English", 2),
    SCIENCE("Science", 3),
    ARTS("Arts", 4),
    MUSIC("Music", 5);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String displaySubject;

    private final int displayId;

    Subjects(String displaySubject, int displayId) {
        this.displaySubject = displaySubject;
        this.displayId = displayId;
    }

    public Long getId() {
        return id;
    }

    public String getDisplaySubject() {
        return displaySubject;
    }

    public int getDisplayId() {
        return displayId;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "displaySubject='" + displaySubject + '\'' +
                '}';
    }
}
