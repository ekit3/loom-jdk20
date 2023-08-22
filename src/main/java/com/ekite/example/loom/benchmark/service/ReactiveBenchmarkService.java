package com.ekite.example.loom.benchmark.service;

import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveBenchmarkService {

    private final QueueSimulatorService queueSimulatorService;

    public Mono<BenchmarkResult> benchmark(Integer nbTask) {
        BenchmarkResult br = BenchmarkResult.builder().name("reactive").build();
        br.start(nbTask);

        try {
            return queueSimulatorService.generateReactiveRequests(nbTask)
                    .map(queueSimulatorService::processRequest)
                    .collectList()
                    .map(res -> {
                        br.setResult(res);
                        br.passed();
                        return br;
                    });

        } catch (Exception e) {
            br.crashed(e.getMessage());
            return Mono.just(br);
        }
    }
}
