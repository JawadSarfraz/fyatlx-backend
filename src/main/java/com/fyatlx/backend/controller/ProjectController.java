package com.fyatlx.backend.controller;

import com.fyatlx.backend.dto.ProjectDto;
import com.fyatlx.backend.entity.Company;
import com.fyatlx.backend.entity.Project;
import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.repository.ProjectRepository;
import com.fyatlx.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sizeMW", required = false) String sizeMW,
            @RequestParam(value = "countryRegion", required = false) String countryRegion,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "estimatedBudget", required = false) String estimatedBudget,
            @RequestParam(value = "deadline", required = false) String deadline,
            @RequestParam(value = "attachedFile", required = false) MultipartFile attachedFile,
            @RequestParam(value = "additionalInfo", required = false) String additionalInfo
    ) throws MessagingException {

        // Provide defaults if null
        category = (category != null) ? category : "N/A";
        sizeMW = (sizeMW != null) ? sizeMW : "N/A";
        countryRegion = (countryRegion != null) ? countryRegion : "N/A";
        title = (title != null) ? title : "N/A";
        description = (description != null) ? description : "N/A";
        estimatedBudget = (estimatedBudget != null) ? estimatedBudget : "N/A";
        deadline = (deadline != null) ? deadline : "N/A";
        additionalInfo = (additionalInfo != null) ? additionalInfo : "N/A";

        // Build company details string
        String companyDetails = "No company assigned.";
        if (user.getCompany() != null) {
            Company company = user.getCompany();
            companyDetails = String.format("""
                    Name: %s
                    Description: %s
                    Country: %s
                    City: %s
                    Size: %s
                    Certifications: %s
                    Services Offered: %s
                    Highlight Project: %s
                    Machinery: %s
                    """,
                    company.getName(),
                    safe(company.getDescription()),
                    safe(company.getCountry()),
                    safe(company.getCity()),
                    safe(company.getSize()),
                    safe(company.getCertifications()),
                    safe(company.getServicesOffered()),
                    safe(company.getHighlightProject()),
                    safe(company.getMachinery())
            );
        }

        // Build email body
        String emailBody = String.format("""
                New Project Submission:

                From: %s (%s)

                Company Info:
                %s

                Category: %s
                Size (MW): %s
                Country/Region: %s

                Title: %s
                Description: %s
                Estimated Budget: %s
                Deadline: %s
                Additional Info: %s
                """,
                user.getName(), user.getEmail(),
                companyDetails,
                category, sizeMW, countryRegion,
                title, description, estimatedBudget, deadline, additionalInfo
        );

        // Send email
        emailService.sendSubmissionEmail(
                "tlf@fyatlx.com",
                "New Project Submission",
                emailBody,
                (attachedFile != null && !attachedFile.isEmpty()) ? attachedFile.getOriginalFilename() : null,
                (attachedFile != null && !attachedFile.isEmpty()) ? attachedFile::getInputStream : null
        );

        return ResponseEntity.ok("Project submitted & email sent!");
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProjectDto>> getUserProjects(@AuthenticationPrincipal User user) {
        List<Project> projects = projectRepository.findByUser(user);
        List<ProjectDto> dtoList = projects.stream()
                .map(ProjectDto::from)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    // Helper to avoid nulls in string formatting
    private String safe(String input) {
        return input != null ? input : "N/A";
    }
}