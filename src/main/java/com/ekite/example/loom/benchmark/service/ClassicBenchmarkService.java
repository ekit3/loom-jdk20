package com.ekite.example.loom.benchmark.service;

import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassicBenchmarkService {

    private final QueueSimulatorService queueSimulatorService;

    public BenchmarkResult benchmark(Integer nbTask) {
        BenchmarkResult br = BenchmarkResult.builder().name("classic").build();
        br.start(nbTask);
        try {
            List<String> result = queueSimulatorService.generateRequests(nbTask)
                    .parallelStream()
                    .map(queueSimulatorService::processRequest)
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
