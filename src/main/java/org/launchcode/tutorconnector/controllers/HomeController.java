package org.launchcode.tutorconnector.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
@Controller
public class HomeController {


    @GetMapping
    public String renderHomePage(Model model) {
        return "index";
    }


    @GetMapping("/resources")
    public String renderLearningResources(Model model) {
        return "resources";
    }


}
