package com.ekite.example.loom.benchmark.service.bma;

import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThreadBenchmarkService {

    private final QueueSimulatorService queueSimulatorService;

    public BenchmarkResult benchmark(Integer nbTask) {
        BenchmarkResult br = BenchmarkResult.builder().name("thread").build();
        br.start(nbTask);

        try {
            List<String> result = queueSimulatorService.generateRequests(nbTask)
                    .stream()
                    .map(req -> CompletableFuture.supplyAsync(() -> queueSimulatorService.processRequest(req)))
                    .map(CompletableFuture::join)
                    .toList();

            br.setResult(result);

        } catch (Exception e) {
            br.crashed(e.getMessage());
            return br;
        }

        br.passed();
        return br;
    }
}
