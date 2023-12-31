package org.launchcode.tutorconnector.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.dto.LoginFormDTO;
import org.launchcode.tutorconnector.models.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

public class TutorAuthController {

    @Autowired
    private TutorRepository tutorRepository;

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

    @GetMapping("/tutor/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegistrationFormDTO());
        // Send value of logged in boolean
        model.addAttribute("loggedIn", session.getAttribute("tutor") !=null);
        return "register";
    }

    @PostMapping("/tutor/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO, Errors errors, HttpServletRequest request) {
        // Send tutor back to form if errors are found

        if(errors.hasErrors()) {
            return "register";
        }
        // Send tutor back if email already exists
        Tutor existingTutor = tutorRepository.findByEmail(RegistrationFormDTO.getEmail());

        if(existingTutor != null) {
            errors.rejectValue("email", "email.alreadyExists", "An account with that email already exists.");
            return "register";
        }
        // Send tutor back if passwords don't match
        String password = RegistrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }
        //If no errors, save new email and password, start new session, redirect to tutor profile
        Tutor newTutor = new Tutor(RegistrationFormDTO.getEmail(), RegistrationFormDTO.getPassword());
        tutorRepository.save(newTutor);
        setTutorInSession(request.getSession(), newTutor);
        return "redirect:/tutor-profile";
    }
// Login forms
    @GetMapping("/tutor/login")
    public String displayLoginForm(Model model, HttpSession session) {
        model.addAttribute(new LoginFormDTO()); //loginFormDTO
        //SEND VALUE OF LOGGEDIN BOOLEAN
        model.addAttribute("loggedIn", session.getAttribute("tutor") !=null);
        return "login";
    }

    @PostMapping("/tutor/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return "login";
        }
        Tutor theTutor = tutorRepository.findByEmail(LoginFormDTO.getEmail());

        String password = LoginFormDTO.getPassword();
        //check both. security through obscurity
        if(theTutor == null || !theTutor.isMatchingPassword(password)) {
            errors.rejectValue("password",
                    "Login.invalid",
                    "Incorrect email/password. Please try again."
            );
            return "login";
        }
        setTutorInSession(request.getSession(), theTutor);
        return "redirect:/tutor-profile";
    }

    //Logout
    @GetMapping("/tutor/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
