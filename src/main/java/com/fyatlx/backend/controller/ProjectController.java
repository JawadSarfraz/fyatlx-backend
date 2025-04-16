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

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final EmailService emailService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitProject(
            @AuthenticationPrincipal User user,
            @RequestPart("form") ProjectSubmissionRequest request,
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
                request.getTitle(), request.getDescription()
        );

        emailService.sendSubmissionEmail(
                "your_email@gmail.com", // your inbox
                "New Project Submission",
                emailBody,
                file.getOriginalFilename(),
                file::getInputStream
        );

        return ResponseEntity.ok("Submission sent successfully");
    }
}
