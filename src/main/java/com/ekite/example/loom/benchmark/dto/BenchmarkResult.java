package com.ekite.example.loom.benchmark.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class BenchmarkResult {

    String name;

    Integer nbTaskRequested;

    LocalDateTime startTime;

    LocalDateTime endTime;

    long elapsedTime;

    boolean crashed;

    String exceptionMessage;

    boolean passed;

    @JsonIgnore
    List<String> result;

    public void start(Integer nbTaskRequested) {
        this.nbTaskRequested = nbTaskRequested;
        startTime = LocalDateTime.now();
    }

    private void stop() {
        endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        elapsedTime = duration.toMillis();
    }

    public void crashed(String message) {
        crashed = true;
        passed = false;
        exceptionMessage = message;
        stop();
    }

    public void passed() {
        crashed = false;
        passed = true;
        stop();
    }


}
