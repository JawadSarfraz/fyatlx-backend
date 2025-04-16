package com.fyatlx.backend.controller;

import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.entity.Company;
import com.fyatlx.backend.repository.CompanyRepository;
import com.fyatlx.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal User user) {
        Company company = user.getCompany();
        return ResponseEntity.ok(company);
    }
}
