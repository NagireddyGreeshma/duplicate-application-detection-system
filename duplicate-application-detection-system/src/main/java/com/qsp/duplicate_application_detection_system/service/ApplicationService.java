package com.qsp.duplicate_application_detection_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qsp.duplicate_application_detection_system.entity.Application;
import com.qsp.duplicate_application_detection_system.entity.DuplicateCheck;
import com.qsp.duplicate_application_detection_system.repository.ApplicationRepository;
import com.qsp.duplicate_application_detection_system.repository.DuplicateCheckRepository;
import com.qsp.duplicate_application_detection_system.util.NameSimilarityUtil;

@Service
public class ApplicationService {
	

	private final ApplicationRepository repository;
    private final DuplicateCheckRepository duplicateRepository;

    private static final double SIMILARITY_THRESHOLD = 85.0;

    public ApplicationService(
            ApplicationRepository repository,
            DuplicateCheckRepository duplicateRepository) {

        this.repository = repository;
        this.duplicateRepository = duplicateRepository;
    }

    public String registerApplication(Application app) {

    	 if (app.getName() == null || app.getName().isBlank()
    	            || app.getEmail() == null || app.getEmail().isBlank()
    	            || app.getPhone() == null || app.getPhone().isBlank()) {
    	        throw new IllegalArgumentException(
    	                "Name, email, and phone are required.");
    	    }

    	    String name = app.getName().trim();
    	    String email = app.getEmail().trim();
    	    String phone = app.getPhone().trim();

    	    app.setName(name);
    	    app.setEmail(email);
    	    app.setPhone(phone);

    	    List<Application> applications = repository.findAll();

    	    for (Application old : applications) {

    	        boolean sameEmail =
    	                old.getEmail() != null
    	                && old.getEmail().trim().equalsIgnoreCase(email);

    	        boolean samePhone =
    	                old.getPhone() != null
    	                && old.getPhone().trim().equals(phone);

    	        // Duplicate if EITHER email OR phone matches.
    	        if (sameEmail || samePhone) {

    	            String reason;

    	            if (sameEmail && samePhone) {
    	                reason = "Email and phone both match";
    	            } else if (sameEmail) {
    	                reason = "Email already exists";
    	            } else {
    	                reason = "Phone number already exists";
    	            }

    	            boolean reportExists =
    	                    duplicateRepository
    	                    .existsByExistingApplicationIdAndSubmittedEmailIgnoreCaseAndSubmittedPhone(
    	                            old.getId(), email, phone);

    	            if (!reportExists) {
    	                DuplicateCheck report = new DuplicateCheck(
    	                        old.getId(),
    	                        name,
    	                        email,
    	                        phone,
    	                        reason,
    	                        null
    	                );

    	                duplicateRepository.save(report);
    	            }

    	            return "Duplicate application detected! " + reason;
    	        }
    	    }

    	    repository.save(app);

    	    return "Application registered successfully!";
    }

    public List<Application> getAllApplications() {
        return repository.findAll();
    }

    public List<DuplicateCheck> getDuplicateReports() {
        return duplicateRepository.findAll();
    }

}
