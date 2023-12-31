package org.launchcode.tutorconnector.controllers;

import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    StudentRepository studentRepository;



    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";


    @GetMapping("/upload")
    public String displayUploadForm(){
        return "imageupload/index";

    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameandPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameandPath,file.getBytes());
        model.addAttribute("msg", "Uploaded Image" + fileNames.toString());
        return "imageupload/index";
    }
}
