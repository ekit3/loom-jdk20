package com.ekite.example.loom;

import com.ekite.example.loom.benchmark.controller.BenchmarkController;
import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
class LoomApplicationTests {


    @Autowired
    private BenchmarkController controller;

    @Test
    void runBenchmarks() {
        Arrays.asList(1, 10, 100, 1000, 1000000, 4000000).forEach(this::benchmark);
    }

    private void benchmark(Integer nbTask) {
        int nbRun = 1;

        List<BenchmarkResult> classicResults = new ArrayList<>();
        List<BenchmarkResult> loomResults = new ArrayList<>();
        List<BenchmarkResult> threadResults = new ArrayList<>();
        List<BenchmarkResult> reactiveResults = new ArrayList<>();

        IntStream.range(0, nbRun)
                .forEach(run -> {
                    classicResults.add(controller.classic(nbTask));
                    loomResults.add(controller.loom(nbTask));
                    threadResults.add(controller.thread(nbTask));
                    StepVerifier.create(controller.reactive(nbTask))
                            .expectNextMatches(reactiveResults::add)
                            .verifyComplete();
                });

        computeResult(classicResults, nbTask);
        computeResult(loomResults, nbTask);
        computeResult(threadResults, nbTask);
        computeResult(reactiveResults, nbTask);
    }

    private void computeResult(List<BenchmarkResult> results, Integer tasks) {
        var name = results.stream().map(BenchmarkResult::getName).distinct().findFirst().orElse(null);
        var elapsedTime = results.stream().map(BenchmarkResult::getElapsedTime).toList();
        var minElapsedTime = Collections.min(elapsedTime);
        var maxElapsedTime = Collections.max(elapsedTime);
        var averageElapsedTime = elapsedTime.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        log.info("--- Computed Result for {} Benchmark with {} tasks ---", name, tasks);
        log.info(" min elapsed time {} ms", minElapsedTime);
        log.info(" max elapsed time {} ms", maxElapsedTime);
        log.info(" average elapsed time {} ms", averageElapsedTime);
        log.info("--------------------------------------------------------------");

    }

}

