package com.qsp.duplicate_application_detection_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.qsp.duplicate_application_detection_system.entity.DuplicateCheck;

public interface DuplicateCheckRepository extends JpaRepository<DuplicateCheck, Long> {
	 boolean existsByExistingApplicationIdAndSubmittedEmailIgnoreCaseAndSubmittedPhone(
	            Long existingApplicationId,
	            String submittedEmail,
	            String submittedPhone);

}
