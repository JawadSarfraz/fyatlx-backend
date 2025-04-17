package com.fyatlx.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String estimatedBudget;
    private LocalDate deadline;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] fileData;

    @ManyToOne
    private User user;
}