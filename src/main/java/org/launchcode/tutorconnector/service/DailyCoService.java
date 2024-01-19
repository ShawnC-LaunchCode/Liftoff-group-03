package org.launchcode.tutorconnector.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DailyCoService {

    private final RestTemplate restTemplate; //makes HTTP requests & spring web dependency

    private final String apiKey;

    public DailyCoService(RestTemplate restTemplate, @Value("${DAILY_CO_API_KEY}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    //create a new meeting room in Daily.co
    public Map<String, Object> createMeeting(String roomName) {
        String url = "https://api.daily.co/v1/rooms/"; // URL for Daily endpoint

        // Set headers for API access
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); //authorization header
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", roomName);

        // Create request entity
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // Send POST request to the API
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException e) { //handles client-side HTTP status code and error messages
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("status", e.getStatusCode().toString());
            errorDetails.put("error", e.getResponseBodyAsString());


            return errorDetails; // Returning the error details for now
        }
    }

    public Map<String, Object> getMeetingDetails(String roomName) {
        String url = "https://api.daily.co/v1/rooms/" + roomName; // URL to get room details

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); //authorization header
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<?> request = new HttpEntity<>(headers);

        // Send GET request
        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("status", e.getStatusCode().toString());
            errorDetails.put("error", e.getResponseBodyAsString());
            return errorDetails;
        }

    }
}

