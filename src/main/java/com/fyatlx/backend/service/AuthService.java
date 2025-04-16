package com.fyatlx.backend.service;

import com.fyatlx.backend.dto.AuthResponse;
import com.fyatlx.backend.dto.LoginRequest;
import com.fyatlx.backend.dto.RegisterRequest;
import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.entity.Role;
import com.fyatlx.backend.entity.Company;
import com.fyatlx.backend.repository.UserRepository;
import com.fyatlx.backend.repository.RoleRepository;
import com.fyatlx.backend.repository.CompanyRepository;
import com.fyatlx.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        // Find or create company
        Company company = companyRepository.findByName(request.getCompanyName())
            .orElseGet(() -> {
                Company newCompany = new Company();
                newCompany.setName(request.getCompanyName());
                return companyRepository.save(newCompany);
            });

        // Check for duplicate user in same domain
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists with this email.");
        }

        Role role = roleRepository.findByName(request.getRole())
            .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompany(company);
        user.setRole(role);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}