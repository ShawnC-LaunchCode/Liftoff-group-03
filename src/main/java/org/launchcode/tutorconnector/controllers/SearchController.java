package org.launchcode.tutorconnector.controllers;
import org.launchcode.tutorconnector.models.Search;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private TutorRepository tutorRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();


    public SearchController() {
        columnChoices.put("all", "All");
        columnChoices.put("firstName", "Tutor First Name");
        columnChoices.put("lastName", "Tutor Last Name");
    }



    @RequestMapping("")
    public String search(Model model) {
        model.addAttribute("columns",columnChoices);
        return "search";
    }


    @PostMapping("results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm){
        Iterable<Tutor> tutors;
        if (searchType.equals("all") || searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            tutors = tutorRepository.findAll();
        } else {
            tutors = Search.findTutorsByColumnAndValue(searchType, searchTerm, tutorRepository.findAll());
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("title", "Tutors with " + columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("tutors", tutors);

        return "search";
    }

}
