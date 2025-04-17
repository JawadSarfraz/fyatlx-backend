package com.fyatlx.backend.repository;

import com.fyatlx.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}