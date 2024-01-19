package org.launchcode.tutorconnector.controllers;

import jakarta.validation.Valid;
import org.launchcode.tutorconnector.models.Contact;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.TutorReview;
import org.launchcode.tutorconnector.models.data.ContactRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.launchcode.tutorconnector.models.data.TutorReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("contact")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;


    @GetMapping("")
    public String displayAddReviewForm(Model model) {
        model.addAttribute(new Contact());
        return  "contact/contact";
    }


    @PostMapping("")
    public String processAddContact(@ModelAttribute @Valid Contact newContact,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "contact/contact";
        }
        contactRepository.save(newContact);
        model.addAttribute("results", "Message Sent");
        return "contact/contact";
    }

    @GetMapping("view")
    public String displayMessages(Model model) {

            model.addAttribute("contacts", contactRepository.findAll());
            return  "contact/view";

    }
}
