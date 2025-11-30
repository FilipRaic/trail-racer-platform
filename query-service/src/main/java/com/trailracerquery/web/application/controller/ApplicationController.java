package com.trailracerquery.web.application.controller;

import com.trailracerquery.web.application.dto.ApplicationListResponse;
import com.trailracerquery.web.application.dto.ApplicationResponse;
import com.trailracerquery.web.application.service.ApplicationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('APPLICANT')")
    public ResponseEntity<@NonNull List<ApplicationListResponse>> getApplications() {
        return ResponseEntity.ok(applicationService.getApplications());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('APPLICANT')")
    public ResponseEntity<@NonNull ApplicationResponse> getApplicationById(@PathVariable UUID id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
}
