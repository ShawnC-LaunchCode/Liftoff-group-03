package org.launchcode.tutorconnector.models;

public enum GradeLevel {

    GRADUATE("Graduate"),
    UNDERGRADUATE("Undergraduate"),
    HIGH_SCHOOL("High School"),
    ;

    private final String displayGradeLevel;

    GradeLevel(String displayGradeLevel) {
        this.displayGradeLevel = displayGradeLevel;
    }

    public String getDisplayGradeLevel() {
        return displayGradeLevel;
    }
}
