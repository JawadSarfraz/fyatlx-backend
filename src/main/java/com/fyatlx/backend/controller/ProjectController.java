package com.fyatlx.backend.controller;

import com.fyatlx.backend.dto.ProjectDto;
import com.fyatlx.backend.entity.Project;
import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.repository.ProjectRepository;
import com.fyatlx.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final EmailService emailService;
    private final ProjectRepository projectRepository;

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> submitProject(
        @AuthenticationPrincipal User user,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "description", required = false) String description,
        @RequestParam(value = "estimatedBudget", required = false) String budget,
        @RequestParam(value = "deadline", required = false) String deadline,
        @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile
) throws MessagingException {
    StringBuilder emailBodyBuilder = new StringBuilder();

    emailBodyBuilder.append("New Project Submission:\n\n")
            .append("From: ").append(user.getName()).append(" (").append(user.getEmail()).append(")\n")
            .append("Company: ").append(user.getCompany().getName()).append("\n");

    if (title != null) {
        emailBodyBuilder.append("Title: ").append(title).append("\n");
    }
    if (description != null) {
        emailBodyBuilder.append("Description: ").append(description).append("\n");
    }
    if (budget != null) {
        emailBodyBuilder.append("Budget: ").append(budget).append("\n");
    }
    if (deadline != null) {
        emailBodyBuilder.append("Deadline: ").append(deadline).append("\n");
    }

    emailService.sendSubmissionEmail(
            "jawadsarfraz96@gmail.com",
            "New Project Submission",
            emailBodyBuilder.toString(),
            attachedFile != null ? attachedFile.getOriginalFilename() : null,
            attachedFile != null ? attachedFile::getInputStream : null
    );

    return ResponseEntity.ok("Project submitted & email sent!");
}

//     @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public ResponseEntity<?> submitProject(
//             @AuthenticationPrincipal User user,
//             @RequestParam("title") String title,
//             @RequestParam("description") String description,
//             @RequestParam("estimatedBudget") String budget,
//             @RequestParam("deadline") String deadline,
//             @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile
//     ) throws MessagingException {
//         String emailBody = String.format("""
//                 New Project Submission:

//                 From: %s (%s)
//                 Company: %s
//                 Title: %s
//                 Description: %s
//                 Budget: %s
//                 Deadline: %s
//                 """,
//                 user.getName(), user.getEmail(), user.getCompany().getName(),
//                 title, description, budget, deadline
//         );

//         emailService.sendSubmissionEmail(
//                 "jawadsarfraz96@gmail.com",
//                 "New Project Submission",
//                 emailBody,
//                 attachedFile != null ? attachedFile.getOriginalFilename() : null,
//                 attachedFile != null ? attachedFile::getInputStream : null
//         );

//         return ResponseEntity.ok("Project submitted & email sent!");
//     }

    @GetMapping("/mine")
    public ResponseEntity<List<ProjectDto>> getUserProjects(@AuthenticationPrincipal User user) {
        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectDto> dtoList = projects.stream()
                .map(ProjectDto::from)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}

//@RequiredArgsConstructor  // You can either comment this or leave it

// @RestController
// @RequestMapping("/api/project")
// public class ProjectController {

//     // private final EmailService emailService;
//     private final ProjectRepository projectRepository;

//     public ProjectController(ProjectRepository projectRepository) {
//         this.projectRepository = projectRepository;
//     }

//     @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//     public ResponseEntity<?> submitProject(
//             @AuthenticationPrincipal User user,
//             @RequestParam("title") String title,
//             @RequestParam("description") String description,
//             @RequestParam("estimatedBudget") String budget,
//             @RequestParam("deadline") String deadline,
//             @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile
//     ) {
//         String emailBody = String.format("""
//                 New Project Submission:

//                 From: %s (%s)
//                 Company: %s
//                 Title: %s
//                 Description: %s
//                 Budget: %s
//                 Deadline: %s
//                 """,
//                 user.getName(), user.getEmail(), user.getCompany().getName(),
//                 title, description, budget, deadline
//         );

//         // emailService.sendSubmissionEmail(...); // TEMPORARILY DISABLED

//         return ResponseEntity.ok("Project submitted! (email sending skipped)");
//     }
// }