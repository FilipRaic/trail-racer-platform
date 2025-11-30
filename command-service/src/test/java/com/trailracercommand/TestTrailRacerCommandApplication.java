package com.trailracercommand;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestTrailRacerCommandApplication {

    static void main(String[] args) {
        SpringApplication.from(TrailRacerCommandApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
