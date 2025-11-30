package com.trailracerquery.web.application.repository;

import com.trailracerquery.web.application.model.Application;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<@NonNull Application, @NonNull UUID> {

    List<Application> findAllByUserId(Long userId);
}
