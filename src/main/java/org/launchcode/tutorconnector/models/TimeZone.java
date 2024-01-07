package org.launchcode.tutorconnector.models;

public enum TimeZone {
    //fixed set of timezones
    EASTERN_TIMEZONE("EST"),
    CENTRAL_TIMEZONE("CST"),
    MOUNTAIN_TIMEZONE("MST"),
    PACIFIC_TIMEZONE("PST");

    private final String displayName;

    TimeZone(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
