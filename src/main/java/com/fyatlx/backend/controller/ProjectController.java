package com.fyatlx.backend.controller;

import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final EmailService emailService;

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> submitProject(
            @AuthenticationPrincipal User user,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("estimatedBudget") String estimatedBudget,
            @RequestParam("deadline") String deadline,
            @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile
    ) throws MessagingException {

        String emailBody = String.format("""
            New Project Submission:

            From: %s (%s)
            Company: %s

            Title: %s
            Description: %s
            Budget: %s
            Deadline: %s
            """,
            user.getName(), user.getEmail(), user.getCompany().getName(),
            title, description, estimatedBudget, deadline
        );

        emailService.sendSubmissionEmail(
                "jawadsarfraz96@gmail.com",
                "New Project Submission",
                emailBody,
                attachedFile != null ? attachedFile.getOriginalFilename() : null,
                attachedFile != null ? attachedFile::getInputStream : null
        );

        return ResponseEntity.ok("Project submitted & email sent!");
    }
    @GetMapping("/mine")
    public ResponseEntity<List<ProjectDto>> getUserProjects(@AuthenticationPrincipal User user) {
        List<Project> projects = projectRepository.findByUser(user);
        return ResponseEntity.ok(projects.stream().map(ProjectDto::from).toList());
    }

}