package com.trailracercommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan
@EnableScheduling
@SpringBootApplication
public class TrailRacerCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrailRacerCommandApplication.class, args);
    }
}
