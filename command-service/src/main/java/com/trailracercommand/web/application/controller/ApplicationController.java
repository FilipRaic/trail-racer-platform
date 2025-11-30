package com.trailracercommand.web.application.controller;

import com.trailracercommand.web.application.dto.CreateApplicationRequest;
import com.trailracercommand.web.application.dto.DeleteApplicationRequest;
import com.trailracercommand.web.application.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<@NonNull UUID> createApplication(@Valid @RequestBody CreateApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.create(request));
    }

    @DeleteMapping
    public ResponseEntity<@NonNull Void> deleteApplication(@Valid @RequestBody DeleteApplicationRequest request) {
        applicationService.delete(request);

        return ResponseEntity.ok().build();
    }
}
