package org.launchcode.tutorconnector.controllers;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Subject;
import org.launchcode.tutorconnector.models.Subjects;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.launchcode.tutorconnector.models.data.SubjectRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.launchcode.tutorconnector.models.data.TutorReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("studentlist")
public class StudentListController {



    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Autowired
    private TutorReviewRepository tutorReviewRepository;



    public StudentListController () {}

    public List<Student> students = new ArrayList<>();


    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("view","all");
        return "studentlist";
    }


}
