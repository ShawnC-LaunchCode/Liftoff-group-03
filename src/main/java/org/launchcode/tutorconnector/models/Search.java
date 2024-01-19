package org.launchcode.tutorconnector.models;


import java.util.ArrayList;


public class Search {


    public static ArrayList<Tutor> findTutorsByColumnAndValue(String column, String value, Iterable<Tutor> allTutors) {

        ArrayList<Tutor> results = new ArrayList<>();

        for (Tutor tutor : allTutors) {

            String aValue = getTutorFieldValue(tutor, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(tutor);
            }
        }

        return results;
    }

    public static String getTutorFieldValue(Tutor tutor, String fieldName){
        String theValue;
        if (fieldName.equals("firstName")){
            theValue = tutor.getFirstName();
        } else if (fieldName.equals("lastName")){
            theValue = tutor.getLastName();
        } else {
            theValue = tutor.getFirstName();
        }

        return theValue;
    }

}
