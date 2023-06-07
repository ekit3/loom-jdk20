package com.ekite.example.loom.benchmark.service.bma;

import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoomBenchmarkService {

    private final QueueSimulatorService queueSimulatorService;

    public BenchmarkResult benchmark(Integer nbTask) {
        BenchmarkResult br = BenchmarkResult.builder().name("loom").build();
        br.start(nbTask);


        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            br.setResult(new ArrayList<>());

            List<Future<String>> futures = queueSimulatorService.generateRequests(nbTask)
                    .stream()
                    .map(req -> executor.submit(() -> queueSimulatorService.processRequest(req)))
                    .toList();
            // executor.close() is called implicitly, and waits


            for (Future<String> future : futures) {
                br.getResult().add(future.get());
            }

        } catch (Exception e) {
            br.crashed(e.getMessage());
            return br;
        }

        br.passed();
        return br;
    }
}
