package org.launchcode.tutorconnector.controllers.api;

import org.launchcode.tutorconnector.models.MeetingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.launchcode.tutorconnector.service.DailyCoService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/")
public class ApiMeetingController {


    private final DailyCoService dailyCoService;

    @Autowired
    public ApiMeetingController(DailyCoService dailyCoService) {
        this.dailyCoService = dailyCoService;
    }

    @PostMapping("/create-meeting")
    public ResponseEntity<?> createMeeting(@RequestBody MeetingForm meetingForm) {
        try {
            Map<String, Object> meetingDetails = dailyCoService.createMeeting(meetingForm.getRoomName());
            return ResponseEntity.ok(meetingDetails);
        } catch (Exception e) {
            // Handle exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}

