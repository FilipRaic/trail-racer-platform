package com.trailracerquery.web.race.service;

import com.trailracerquery.shared.exception.CustomException;
import com.trailracerquery.shared.mapper.Mapper;
import com.trailracerquery.web.race.dto.RaceListResponse;
import com.trailracerquery.web.race.dto.RaceResponse;
import com.trailracerquery.web.race.model.Race;
import com.trailracerquery.web.race.repository.RaceRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.trailracerquery.shared.exception.ErrorMessage.RACE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RaceService {

    private final Mapper mapper;
    private final RaceRepository raceRepository;

    @Transactional
    public List<RaceListResponse> getRaces() {
        Pageable pageable = Pageable.unpaged(Sort.by(Sort.Direction.ASC, "startDateTimeUtc"));
        Page<@NonNull Race> races = raceRepository.findAll(pageable);

        return mapper.mapList(races.getContent(), RaceListResponse.class);
    }

    @Transactional
    public RaceResponse getRaceById(@NonNull UUID id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new CustomException(RACE_NOT_FOUND));

        return mapper.map(race, RaceResponse.class);
    }
}
