package org.launchcode.tutorconnector.controllers;

import jakarta.validation.Valid;
import org.hibernate.annotations.AnyKeyJavaType;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.TutorReview;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.launchcode.tutorconnector.models.data.TutorReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("tutorreviews")
public class TutorReviewController {
    @Autowired
    private TutorReviewRepository tutorReviewRepository;

    @Autowired
    private TutorRepository tutorRepository;


    @GetMapping("add/{tutorId}")
    public String displayAddReviewForm(Model model, @PathVariable int tutorId) {
        Optional optTutor = tutorRepository.findById(tutorId);
        if (optTutor.isPresent()) {
            Tutor tutor = (Tutor) optTutor.get();
            model.addAttribute("tutor", tutor);
            return  "tutor/reviews/add";
        } else {
            return "redirect:../";
        }
    }


    @PostMapping("add")
    public String processAddTutorReviewform(Model model,@RequestParam String subject, @RequestParam String review, @RequestParam int tutorId) {

        TutorReview tutorReview = new TutorReview();
        Tutor tutor;
        Optional optTutor = tutorRepository.findById(tutorId);
        if (optTutor.isPresent()) {
            tutor = (Tutor) optTutor.get();
            tutorReview.setTutor(tutor);
        }
        else {
            return "redirect:../";
        }
        tutorReview.setReview(review);
        tutorReview.setSubject(subject);
        tutorReviewRepository.save(tutorReview);
        model.addAttribute("tutor", tutor);
        model.addAttribute("tutorReviews", tutor.getTutorReviews());
        return  "tutor/reviews/view";
    }

    @GetMapping("view/{tutorId}")
    public String displayViewTutorReviews(Model model, @PathVariable int tutorId) {

        Optional optTutor = tutorRepository.findById(tutorId);
        if (optTutor.isPresent()) {
            Tutor tutor = (Tutor) optTutor.get();
            model.addAttribute("tutor", tutor);
            model.addAttribute("tutorReviews", tutor.getTutorReviews());
            return  "tutor/reviews/view";
        } else {
            return "redirect:../";
        }

    }

}
