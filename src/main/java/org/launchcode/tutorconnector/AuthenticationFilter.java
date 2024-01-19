package org.launchcode.tutorconnector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.launchcode.tutorconnector.controllers.StudentAuthController;
import org.launchcode.tutorconnector.controllers.TutorAuthController;
import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Tutor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {


    @Autowired
    StudentAuthController studentAuthController;

    @Autowired
    TutorAuthController tutorAuthController;

    private static final List<String> whitelist = Arrays.asList("/index", "/student/register", "/tutor/register","/contact", "/resources", "/login", "/css", "/images", "/logout");

    // Checks all pages and static resources against blacklist
    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.equals("/") || path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // Don't require sign-in for whitelisted pages
        if (isWhitelisted(request.getRequestURI())) {
            // Early return to allow request to go through
            return true;
        }

        HttpSession session = request.getSession();
        Student student = studentAuthController.getStudentFromSession(session);
        Tutor tutor = tutorAuthController.getTutorFromSession(session);
//if user is logged in
        if (tutor != null || student != null) {
            return true;
        }
        //if user NOT logged in
        response.sendRedirect("/login");
        return false;

    }

    }

