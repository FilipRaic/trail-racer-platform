package com.trailracerquery.web.application.service;

import com.trailracerquery.shared.exception.CustomException;
import com.trailracerquery.shared.mapper.Mapper;
import com.trailracerquery.shared.security.UserSecurity;
import com.trailracerquery.web.application.dto.ApplicationListResponse;
import com.trailracerquery.web.application.dto.ApplicationResponse;
import com.trailracerquery.web.application.model.Application;
import com.trailracerquery.web.application.repository.ApplicationRepository;
import com.trailracerquery.web.user.enums.Role;
import com.trailracerquery.web.user.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.trailracerquery.shared.exception.ErrorMessage.APPLICATION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final Mapper mapper;
    private final UserSecurity userSecurity;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public List<ApplicationListResponse> getApplications() {
        User user = userSecurity.getCurrentUser();

        List<Application> applications;
        if (user.getRole().equals(Role.ADMIN)) {
            applications = applicationRepository.findAll();
        } else {
            applications = applicationRepository.findAllByUserId(userSecurity.getCurrentUserId());
        }

        return mapper.mapList(applications, ApplicationListResponse.class);
    }

    @Transactional
    public ApplicationResponse getApplicationById(@NonNull UUID id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new CustomException(APPLICATION_NOT_FOUND));

        return mapper.map(application, ApplicationResponse.class);
    }
}
