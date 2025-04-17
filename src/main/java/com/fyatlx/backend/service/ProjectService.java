package com.fyatlx.backend.service;

import com.fyatlx.backend.entity.Project;
import com.fyatlx.backend.entity.User;
import com.fyatlx.backend.repository.ProjectRepository;
import com.fyatlx.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

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