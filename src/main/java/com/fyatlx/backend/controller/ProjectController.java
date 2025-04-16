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
}
