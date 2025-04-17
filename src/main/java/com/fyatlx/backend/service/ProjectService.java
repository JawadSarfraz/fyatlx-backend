package com.fyatlx.backend.service;

import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.entity.Role;
import com.fyatlx.backend.entity.Company;
import com.fyatlx.backend.repository.UserRepository;
import com.fyatlx.backend.repository.RoleRepository;
import com.fyatlx.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public void submitProject(String title, String description, String budget, String deadline, MultipartFile file, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);
        project.setEstimatedBudget(budget);
        project.setDeadline(LocalDate.parse(deadline));
        project.setUser(user);

        if (file != null && !file.isEmpty()) {
            try {
                project.setFileName(file.getOriginalFilename());
                project.setFileType(file.getContentType());
                project.setFileData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to process file", e);
            }
        }

        projectRepository.save(project);
    }
}