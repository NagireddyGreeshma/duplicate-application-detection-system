package com.qsp.duplicate_application_detection_system.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.duplicate_application_detection_system.entity.Application;
import com.qsp.duplicate_application_detection_system.entity.DuplicateCheck;
import com.qsp.duplicate_application_detection_system.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
	 private final ApplicationService service;

	    public ApplicationController(ApplicationService service) {
	        this.service = service;
	    }

	    @PostMapping
	    public ResponseEntity<String> register(
	            @RequestBody Application application) {

	        try {
	            String result =
	                    service.registerApplication(application);

	            if (result.startsWith("Duplicate")
	                    || result.startsWith("Possible duplicate")) {

	                return ResponseEntity.status(HttpStatus.CONFLICT)
	                        .body(result);
	            }

	            return ResponseEntity.ok(result);

	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest()
	                    .body(e.getMessage());
	        }
	    }

	    @GetMapping
	    public List<Application> getAllApplications() {
	        return service.getAllApplications();
	    }

	    @GetMapping("/duplicates")
	    public List<DuplicateCheck> getDuplicateReports() {
	        return service.getDuplicateReports();
	    }

}
