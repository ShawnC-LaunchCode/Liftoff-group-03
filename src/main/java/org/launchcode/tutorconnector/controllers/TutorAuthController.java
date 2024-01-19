package org.launchcode.tutorconnector.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.tutorconnector.models.*;
import org.launchcode.tutorconnector.models.data.LoginRepository;
import org.launchcode.tutorconnector.models.data.SubjectRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.launchcode.tutorconnector.models.dto.EditFormDTO;
import org.launchcode.tutorconnector.models.dto.LoginFormDTO;
import org.launchcode.tutorconnector.models.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tutor")
public class TutorAuthController {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    //the key to store user IDs
    private static final String tutorSessionKey = "tutor";

    //Stores key/value pair with session key and user ID
    private static void setTutorInSession(HttpSession session, Tutor tutor) {
        session.setAttribute(tutorSessionKey, tutor.getId());
    }

    //look up user with key
    public Tutor getTutorFromSession(HttpSession session) {

        Integer tutorId = (Integer) session.getAttribute(tutorSessionKey);
        if(tutorId == null) {
            return null;
        }

        Optional<Tutor> tutorOpt = tutorRepository.findById(tutorId);

        if (tutorOpt.isEmpty()) {
            return null;
        }

        return tutorOpt.get();
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegistrationFormDTO());
        // Send value of logged in boolean
        model.addAttribute("tutorLoggedIn", session.getAttribute("tutor") !=null);
        model.addAttribute("subjects",subjectRepository.findAll());
        return "tutor/register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO, @RequestParam List<Integer> subjects, Errors errors, HttpServletRequest request) {

        // Send tutor back to form if errors are found
        if(errors.hasErrors()) {
            return "tutor/register";
        }
        // Send tutor back if email already exists
        Tutor existingTutor = tutorRepository.findByEmail(registrationFormDTO.getEmail());

        if(existingTutor != null) {
            errors.rejectValue("email", "email.alreadyExists", "An account with that email already exists.");
            return "tutor/register";
        }
        // Send tutor back if passwords don't match
        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "tutor/register";
        }
        //If no errors, save new email and password, start new session, redirect to tutor profile
        Tutor newTutor = new Tutor(registrationFormDTO.getFirstName(), registrationFormDTO.getLastName(), registrationFormDTO.getEmail(), registrationFormDTO.getPassword(), null, null, null);
//        tutorRepository.save(newTutor);
//        setTutorInSession(request.getSession(), newTutor);
            newTutor.setQualifications(registrationFormDTO.getQualifications());
       //     newTutor.setSubjects(registrationFormDTO.getSubjects());
            newTutor.setAvailability(registrationFormDTO.getAvailability());

        List<Subject> subjectObjs = (List<Subject>) subjectRepository.findAllById(subjects);
        newTutor.setSubjects(subjectObjs);


//
        Login newLogin = new Login(registrationFormDTO.getEmail());
            newLogin.setEmail(registrationFormDTO.getEmail());
            newLogin.setRole("tutor");

        tutorRepository.save(newTutor);
        loginRepository.save(newLogin);
        setTutorInSession(request.getSession(), newTutor);
        return "redirect:/tutor/profile/" + newTutor.getId();
    }

    @GetMapping("/profile/edit/{id}")
    public String displayEditForm(@PathVariable int id, Model model, HttpSession session) {
        Tutor loggedInTutor = getTutorFromSession(session);

        // Check if a tutor is logged in and if their ID matches the path variable
        if (loggedInTutor != null && loggedInTutor.getId() == id) {
            Optional<Tutor> optionalTutor = tutorRepository.findById(id);

            if (optionalTutor.isPresent()) {
                Tutor tutor = optionalTutor.get();
                model.addAttribute("tutor", tutor);
                model.addAttribute("editForm", new EditFormDTO(tutor));
                return "tutor/edit";
            } else {
                //if tutor id doesnt exist
                return "redirect:/";
            }
        } else {
            // If no tutor is logged in or the ID doesn't match
            return "redirect:/";
        }
    }

    @PostMapping("/profile/edit/{id}")
    public String processEditForm(@PathVariable int id, @ModelAttribute("editForm") EditFormDTO editForm, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "tutor/edit";
        }

        Optional<Tutor> optionalTutor = tutorRepository.findById(id);
        if (optionalTutor.isPresent()) {
            Tutor tutor = optionalTutor.get();
            tutor.setFirstName(editForm.getFirstName());
            tutor.setLastName(editForm.getLastName());
            tutor.setEmail(editForm.getEmail());
            tutor.setQualifications(editForm.getQualifications());
            tutor.setAvailability(editForm.getAvailability());
            tutorRepository.save(tutor);
            return "redirect:/tutor/profile/" + tutor.getId();
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteTutor(@PathVariable int id) {
        Optional<Tutor> tutorOpt = tutorRepository.findById(id);
        if (tutorOpt.isPresent()) {
            tutorRepository.delete(tutorOpt.get());
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTutorAccount(@PathVariable int id, HttpSession session) {
        Tutor loggedInTutor = getTutorFromSession(session);
        if (loggedInTutor != null && loggedInTutor.getId() == id) {
            tutorRepository.deleteById(id);
            session.invalidate(); // Logging out the user after account deletion
            return "redirect:/"; // Redirect to home page after deletion
        } else {
            return "redirect:/tutor/profile/" + id; // Redirect back if not authorized
        }
    }


}
