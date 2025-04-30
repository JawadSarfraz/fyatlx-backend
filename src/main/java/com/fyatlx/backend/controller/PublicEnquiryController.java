package com.fyatlx.backend.controller;

import com.fyatlx.backend.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/public/enquiry")
@RequiredArgsConstructor
public class PublicEnquiryController {

    private final EmailService emailService;

    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitEnquiry(
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "website", required = false) String website,
            @RequestParam(value = "lookingFor", required = false) String lookingFor,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws MessagingException {
        String emailBody = String.format("""
                New Enquiry Submission:

                Company: %s
                Name: %s %s
                Email: %s
                Website: %s
                Looking For: %s
                Description: %s
                """,
                company, firstName, lastName, email, website, lookingFor, description
        );

        emailService.sendSubmissionEmail(
                "tlf@fyatlx.com",
                "New Enquiry Submission",
                emailBody,
                file != null ? file.getOriginalFilename() : null,
                file != null ? file::getInputStream : null
        );

        return ResponseEntity.ok("Enquiry submitted & email sent!");
    }
}
