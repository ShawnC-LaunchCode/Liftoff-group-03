package org.launchcode.tutorconnector.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.launchcode.tutorconnector.models.Event;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.EventRepository;
import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RestController
public class CalendarController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    StudentRepository studentRepository;


    @RequestMapping("/api")
    @ResponseBody
    String home() {
        return "Welcome!";
    }

    @GetMapping("/getUpdatedEvents/{id}")
    @ResponseBody
    public List<Event> getUpdatedEvents(@PathVariable int id) {
        // Fetch updated events from the server (replace with your logic)
        Optional optTutor = tutorRepository.findById(id);
        Tutor tutor = (Tutor) optTutor.get();
        List<Event> updatedEvents = tutor.getEvents();
        return updatedEvents;
    }

    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Event> events(@RequestParam("start") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime start,
                           @RequestParam("end") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime end) {


        return eventRepository.findBetween(start, end);
    }

    @PostMapping("/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event createEvent(Model model, @RequestBody EventCreateParams params) {
        Tutor tutor = new Tutor();
        Event e = new Event();
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setText(params.text);
        Optional optTutor = tutorRepository.findById(params.tutorId);
        if (optTutor.isPresent()) {
            tutor = (Tutor) optTutor.get();
            e.setTutor(tutor);
        }
        Optional optStudent = studentRepository.findById(params.studentId);
        if (optStudent.isPresent()) {
            Student student = (Student) optStudent.get();
            e.setStudent(student);
        }
        eventRepository.save(e);

        return e;
    }

    @PostMapping("/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event moveEvent(@RequestBody EventMoveParams params) {

        Event e = eventRepository.findById(params.id).get();
        e.setStart(params.start);
        e.setEnd(params.end);
        eventRepository.save(e);

        return e;
    }

    @PostMapping("/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Event setColor(@RequestBody SetColorParams params) {

        Event e = eventRepository.findById(params.id).get();
        e.setColor(params.color);
        eventRepository.save(e);

        return e;
    }

    @PostMapping("/api/events/delete")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    EventDeleteResponse deleteEvent(@RequestBody EventDeleteParams params) {

        eventRepository.deleteById(params.id);

        return new EventDeleteResponse() {{
            message = "Deleted";
        }};
    }



    public static class EventDeleteParams {
        public Long id;
    }

    public static class EventDeleteResponse {
        public String message;
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;

        public int tutorId;

        public String subject;
        public int studentId;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }


}
