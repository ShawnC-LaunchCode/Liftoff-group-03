package org.launchcode.tutorconnector.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.tutorconnector.models.Contact;
import org.launchcode.tutorconnector.models.Login;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.LoginRepository;
import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.dto.EditFormDTO;
import org.launchcode.tutorconnector.models.dto.LoginFormDTO;
import org.launchcode.tutorconnector.models.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/student")
public class StudentAuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LoginRepository loginRepository;


    private static final String studentSessionKey = "student";

    private static void setStudentInSession(HttpSession session, Student student) {
        session.setAttribute(studentSessionKey, student.getId());
    }

    public Student getStudentFromSession(HttpSession session) {

        Integer studentId = (Integer) session.getAttribute(studentSessionKey);
        if (studentId == null) {
            return null;
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return null;
        }

        return studentOpt.get();
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegistrationFormDTO());
//      model.addAttribute(new Login());
        //Send value of logged in boolean
        model.addAttribute("studentLoggedIn", session.getAttribute("student") !=null);
        return "student/register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO, Errors errors, HttpServletRequest request) {

        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            return "student/register";
        }
        // Send user back if email already exists
        Student existingStudent = studentRepository.findByEmail(registrationFormDTO.getEmail());

        if (existingStudent != null) {
            errors.rejectValue("email", "email.alreadyExists", "An account with that email already exists.");
            return "student/register";
        }
        // Send user back if passwords don't match
        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "student/register";
        }
        //If no errors, save new email and password, start new session, redirect to userprofile
        Student newStudent = new Student(registrationFormDTO.getFirstName(), registrationFormDTO.getLastName(), registrationFormDTO.getEmail(), registrationFormDTO.getPassword(), null);
        newStudent.setGradeLevel(registrationFormDTO.getGradeLevel());
//            newStudent.setFirstName(registrationFormDTO.getFirstName());
//            newStudent.setLastName(registrationFormDTO.getLastName());
//            newStudent.setPwHash(registrationFormDTO.getPassword());
//            newStudent.setEmail(registrationFormDTO.getEmail());
//
        Login newLogin = new Login(registrationFormDTO.getEmail());
            newLogin.setEmail(registrationFormDTO.getEmail());
            newLogin.setRole("student");

        studentRepository.save(newStudent);
        loginRepository.save(newLogin);
        setStudentInSession(request.getSession(), newStudent);
        return "redirect:/student/profile/" + newStudent.getId();
    }

    @GetMapping("/profile/edit/{id}")
    public String displayEditForm(@PathVariable int id, Model model, HttpSession session) {
        Student loggedInStudent = getStudentFromSession(session);

        // Check if a student is logged in and if their ID matches the path variable
        if (loggedInStudent != null && loggedInStudent.getId() == id) {
            Optional<Student> optStudent = studentRepository.findById(id);

            if (optStudent.isPresent()) {
                Student student = optStudent.get();
                model.addAttribute("student", student);
                model.addAttribute("editForm", new EditFormDTO(student));
                return "student/edit";
            } else {
                // If the student ID doesn't exist
                return "redirect:/";
            }
        } else {
            // If no student is logged in or the ID doesn't match
            return "redirect:/";
        }
    }


    @PostMapping("/profile/edit/{id}")
    public String processEditForm(@PathVariable int id, @ModelAttribute("editForm") EditFormDTO editForm, Errors errors, HttpSession session) {
        if (errors.hasErrors()) {
            return "student/edit";
        }

        Optional<Student> optStudent = studentRepository.findById(id);
        if (optStudent.isPresent()) {
            Student student = optStudent.get();
            student.setFirstName(editForm.getFirstName());
            student.setLastName(editForm.getLastName());
            student.setEmail(editForm.getEmail());
            student.setGradeLevel(editForm.getGradeLevel());
            studentRepository.save(student);
            return "redirect:/student/profile/" + student.getId();
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            studentRepository.delete(studentOpt.get());
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudentAccount(@PathVariable int id, HttpSession session) {
        Student loggedInStudent = getStudentFromSession(session);
        if (loggedInStudent != null && loggedInStudent.getId() == id) {
            studentRepository.deleteById(id);
            session.invalidate(); // Logging out the user after account deletion
            return "redirect:/"; // Redirect to home page after deletion
        } else {
            return "redirect:/student/profile/" + id; // Redirect back if not authorized
        }
    }


}


