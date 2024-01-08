package org.launchcode.tutorconnector.models.dto;

public class StripeResponseDTO {

    // If payment is successful, Stripe returns a response containing payment intent ID & client secret
    private String intentID;

    private String clientSecret;

    public StripeResponseDTO() {
        // Default constructor
    }

    public StripeResponseDTO(String intentID, String clientSecret) {
        this.intentID = intentID;
        this.clientSecret = clientSecret;
    }


    public String getIntentID() {
        return intentID;
    }

    public void setIntentID(String intentID) {
        this.intentID = intentID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
