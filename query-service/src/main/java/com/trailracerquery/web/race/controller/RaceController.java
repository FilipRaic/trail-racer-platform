package com.trailracerquery.web.race.controller;

import com.trailracerquery.web.race.dto.RaceListResponse;
import com.trailracerquery.web.race.dto.RaceResponse;
import com.trailracerquery.web.race.service.RaceService;
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
@RequestMapping("/api/race")
public class RaceController {

    private final RaceService raceService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') || hasRole('APPLICANT')")
    public ResponseEntity<@NonNull List<RaceListResponse>> getRaces() {
        return ResponseEntity.ok(raceService.getRaces());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') || hasRole('APPLICANT')")
    public ResponseEntity<@NonNull RaceResponse> getRaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(raceService.getRaceById(id));
    }
}
