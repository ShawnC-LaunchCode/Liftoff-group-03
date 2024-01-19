package org.launchcode.tutorconnector.controllers;
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
@RequestMapping("list")
public class ListController {



    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TutorReviewRepository tutorReviewRepository;



    public ListController () {}

    public List<Tutor> tutors = new ArrayList<>();


    @RequestMapping("")
    public String list(Model model) {
        model.addAttribute("tutors",tutorRepository.findAll());
        model.addAttribute("view","all");
        return "list";
    }

    @RequestMapping(value = "subject")
    public String listTutorsBySubject(Model model, @RequestParam String column, @RequestParam String value, @RequestParam int id) {
        Iterable<Tutor> tutors;
        if (column.toLowerCase().equals("subject")){
            Optional optSubject = subjectRepository.findById(id);
            if (optSubject.isPresent()) {
                Subject subject = (Subject) optSubject.get();
                model.addAttribute("tutors", subject.getTutors());
                model.addAttribute("title", value + " Tutors");
                model.addAttribute("view","subject");
                return "list";
            } else {
                return "redirect:../";
            }
        } else {
            tutors = tutorRepository.findAll();
            model.addAttribute("tutors", tutors);
            model.addAttribute("title", "All Tutors");
            model.addAttribute("view","all");
            return "list";
        }
    }

    @RequestMapping("student")
    public String listStudents(Model model) {
        model.addAttribute("students",studentRepository.findAll());
        return "student/list";
    }

}
