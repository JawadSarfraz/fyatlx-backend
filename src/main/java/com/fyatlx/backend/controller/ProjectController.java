package com.fyatlx.backend.controller;

import com.fyatlx.backend.dto.ProjectSubmissionRequest;
import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.service.EmailService;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final EmailService emailService;

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitProject(
            @AuthenticationPrincipal User user,
            @RequestPart("title") String title,
            @RequestPart("description") String description,
            @RequestPart("file") MultipartFile file
    ) throws MessagingException {
    
        String emailBody = String.format("""
            New Project Submission:
    
            From: %s (%s)
            Company: %s
            Title: %s
            Description: %s
            """,
            user.getName(), user.getEmail(), user.getCompany().getName(),
            title, description
        );
    
        emailService.sendSubmissionEmail(
                "jawadsarfraz96@gmail.com",
                "New Project Submission",
                emailBody,
                file.getOriginalFilename(),
                file::getInputStream
        );
    
        return ResponseEntity.ok("Submission sent successfully");
    }
    @PostMapping("/submit")
    public ResponseEntity<String> submitProject(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("estimatedBudget") String budget,
            @RequestParam("deadline") String deadline,
            @RequestParam(value = "attachedFile", required = false) MultipartFile file
    ) {
        // You can log or save file here
        System.out.println("Received file: " + (file != null ? file.getOriginalFilename() : "No File"));

        // In MVP: We just log & return response
        return ResponseEntity.ok("Project received!");
    }
    @PostMapping("/submit")
    public ResponseEntity<?> submitProject(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("estimatedBudget") String estimatedBudget,
        @RequestParam("deadline") String deadline,
        @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile,
        Principal principal
    ) {
        projectService.submitProject(title, description, estimatedBudget, deadline, attachedFile, principal.getName());
        return ResponseEntity.ok("Project submitted");
    }

}
