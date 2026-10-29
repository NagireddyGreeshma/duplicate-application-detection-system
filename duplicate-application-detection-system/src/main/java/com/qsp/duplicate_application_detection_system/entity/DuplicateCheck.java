package com.qsp.duplicate_application_detection_system.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "duplicate_checks")
public class DuplicateCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long existingApplicationId;
    private String submittedName;
    private String submittedEmail;
    private String submittedPhone;
    private String matchReason;
    private LocalDateTime detectedAt;
    
    private Double similarityScore;

    public DuplicateCheck() {
    }

 // Constructor for exact email/phone duplicates
 // Constructor for name similarity duplicates
    public DuplicateCheck(
            Long existingApplicationId,
            String submittedName,
            String submittedEmail,
            String submittedPhone,
            String matchReason,
            Double similarityScore) {

        this.existingApplicationId = existingApplicationId;
        this.submittedName = submittedName;
        this.submittedEmail = submittedEmail;
        this.submittedPhone = submittedPhone;
        this.matchReason = matchReason;
        this.similarityScore = similarityScore;
        this.detectedAt = LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }

    public Long getExistingApplicationId() {
        return existingApplicationId;
    }

    public String getSubmittedName() {
        return submittedName;
    }

    public String getSubmittedEmail() {
        return submittedEmail;
    }

    public String getSubmittedPhone() {
        return submittedPhone;
    }

    public String getMatchReason() {
        return matchReason;
    }
    public Double getSimilarityScore() {
        return similarityScore;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

}
