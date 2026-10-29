package com.qsp.duplicate_application_detection_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.duplicate_application_detection_system.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	
	Optional<Application> findFirstByEmailIgnoreCaseOrPhone(String email,String phone);

}
