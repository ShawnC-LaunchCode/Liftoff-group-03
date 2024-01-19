package org.launchcode.tutorconnector.controllers;

import org.launchcode.tutorconnector.models.Student;
import org.launchcode.tutorconnector.models.Tutor;
import org.launchcode.tutorconnector.models.data.StudentRepository;
import org.launchcode.tutorconnector.models.data.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class UploadController {

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    StudentRepository studentRepository;



    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";


    @GetMapping("/tutor/upload/{tutorId}")
    public String displayUploadTutorImage(Model model, @PathVariable int tutorId){
        model.addAttribute("tutorId", tutorId);
        return "imageupload/imageTutor";

    }

    @PostMapping("/tutor/upload")
    public String uploadTutorImage(Model model, @RequestParam("image") MultipartFile file, @RequestParam String tutorId) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameandPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameandPath,file.getBytes());
        Optional optTutor = tutorRepository.findById(Integer.parseInt(tutorId));
        if (optTutor.isPresent()) {
            Tutor tutor = (Tutor) optTutor.get();
            tutor.setImagePath(file.getOriginalFilename());
            tutorRepository.save(tutor);
        }
        model.addAttribute("msg", "Uploaded Image" + fileNames.toString());
        return "redirect:/tutor/profile/" + tutorId;
    }

    @GetMapping("/student/upload/{studentId}")
    public String displayUploadStudentImage(Model model, @PathVariable int studentId){
        model.addAttribute("studentId", studentId);
        return "imageupload/imageStudent";

    }

    @PostMapping("/student/upload")
    public String uploadStudentImage(Model model, @RequestParam("image") MultipartFile file, @RequestParam String studentId) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameandPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameandPath,file.getBytes());
        Optional optStudent = studentRepository.findById(Integer.parseInt(studentId));
        if (optStudent.isPresent()) {
            Student student = (Student) optStudent.get();
            student.setImagePath(file.getOriginalFilename());
            studentRepository.save(student);
        }
        model.addAttribute("msg", "Uploaded Image" + fileNames.toString());
        return "redirect:/student/profile/" + studentId;
    }
}
