package org.launchcode.tutorconnector.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("students", studentRepository.findAll() );
        return "student/index";
    }

    @GetMapping("/profile/{id}")
    public String displayStudentProfile(@PathVariable int id, Model model) {
        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            Student student = optStudent.get();
            model.addAttribute("student", student);
            model.addAttribute("studentId", student.getId());
            return "student/profile";
        } else {
            return "redirect:/register";
        }
    }

//    @PutMapping("/update/{id}")
//    public void updateStudent(@PathVariable int id, Model model) {
//        Optional<Student> optStudent = studentRepository.findById(id);
//        if (optStudent.isPresent()) {
//            Student student = optStudent.get();
//            model.addAttribute("student", student);
//    }

    @RequestMapping("/calendar")
    public String displayCalendar(Model model,@RequestParam String studentId) {
        int value = Integer.parseInt(studentId);
        Optional optStudent = studentRepository.findById(value);
        if (optStudent.isPresent()) {
            Student student = (Student) optStudent.get();
            model.addAttribute("student", student);
            model.addAttribute("events", student.getEvents());
        }
        return "student/calendar";
    }

    }


