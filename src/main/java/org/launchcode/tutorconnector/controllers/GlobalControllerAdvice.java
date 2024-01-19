package org.launchcode.tutorconnector.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;


@ControllerAdvice
@SessionAttributes({"studentId", "tutorId"})
public class GlobalControllerAdvice {

    @Autowired
    private LoginController loginController;

    @ModelAttribute
    public void globalAttributes(Model model, HttpSession session) {

        model.addAttribute("studentLoggedIn", loginController.getStudentFromSession(session) != null);
        model.addAttribute("tutorLoggedIn", loginController.getTutorFromSession(session) != null);
    }
    @ModelAttribute("studentId")
    public Integer getStudentId(HttpSession session) {
        return (Integer) session.getAttribute("studentId");
    }

    @ModelAttribute("tutorId")
    public Integer getTutorId(HttpSession session) {
        return (Integer) session.getAttribute("tutorId");
    }

}
