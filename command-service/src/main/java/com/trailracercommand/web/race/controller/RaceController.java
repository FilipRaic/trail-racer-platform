package com.trailracercommand.web.race.controller;

import com.trailracercommand.web.race.dto.CreateRaceRequest;
import com.trailracercommand.web.race.dto.DeleteRaceRequest;
import com.trailracercommand.web.race.dto.UpdateRaceRequest;
import com.trailracercommand.web.race.service.RaceService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/race")
public class RaceController {

    private final RaceService raceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull UUID> createRace(@Valid @RequestBody CreateRaceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(raceService.create(request));
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull Void> updateRace(@Valid @RequestBody UpdateRaceRequest request) {
        raceService.update(request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull Void> deleteRace(@Valid @RequestBody DeleteRaceRequest request) {
        raceService.delete(request);

        return ResponseEntity.ok().build();
    }
}
