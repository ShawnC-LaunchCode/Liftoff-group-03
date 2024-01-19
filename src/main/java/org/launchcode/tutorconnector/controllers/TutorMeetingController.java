package org.launchcode.tutorconnector.controllers;
import org.launchcode.tutorconnector.models.MeetingForm;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Subject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.launchcode.tutorconnector.service.DailyCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
@RequestMapping("/tutor/meetings")
public class TutorMeetingController {

    private final DailyCoService dailyCoService;

    public TutorMeetingController(DailyCoService dailyCoService) {
        this.dailyCoService = dailyCoService;
    }

    @GetMapping("/create")
    public String showCreateMeetingForm() {
        return "tutor/createMeetings";
    }

    @PostMapping("/create")
    public String processCreateMeetingForm(@ModelAttribute MeetingForm meetingForm, Model model) {
        Map<String, Object> meetingDetails = dailyCoService.createMeeting(meetingForm.getRoomName());
        model.addAttribute("meetingDetails", meetingDetails);
        return "tutor/meetingDetails";
    }

    @GetMapping("/join")
    public String joinMeeting(@RequestParam(required = false) String meetingUrl, Model model) {
        Map<String, Object> meetingDetails = dailyCoService.getMeetingDetails(meetingUrl);

            if (!meetingDetails.containsKey("error")) {
                model.addAttribute("meetingUrl", meetingUrl); // URL for the video call
            } else {
                model.addAttribute("error", "Unable to join the meeting");
            }

            return "tutor/joinMeetings";
        }

}


