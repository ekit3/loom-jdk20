package com.ekite.example.loom.benchmark.controller;

import com.ekite.example.loom.benchmark.dto.BenchmarkResult;
import com.ekite.example.loom.benchmark.service.ClassicBenchmarkService;
import com.ekite.example.loom.benchmark.service.LoomBenchmarkService;
import com.ekite.example.loom.benchmark.service.ReactiveBenchmarkService;
import com.ekite.example.loom.benchmark.service.ThreadBenchmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController("/benchmark")
@RequiredArgsConstructor
public class BenchmarkController {

    private final ClassicBenchmarkService classicService;

    private final ThreadBenchmarkService threadService;

    private final LoomBenchmarkService loomService;

    private final ReactiveBenchmarkService reactiveService;

    @GetMapping("/classic")
    public BenchmarkResult classic(@RequestParam Integer nbTask) {
        return classicService.benchmark(nbTask);
    }

    @GetMapping("/thread")
    public BenchmarkResult thread(@RequestParam Integer nbTask) {
        return threadService.benchmark(nbTask);
    }

    @GetMapping("/reactive")
    public Mono<BenchmarkResult> reactive(@RequestParam Integer nbTask) {
        return reactiveService.benchmark(nbTask);
    }

    @GetMapping("/loom")
    public BenchmarkResult loom(@RequestParam Integer nbTask) {
        return loomService.benchmark(nbTask);
    }

}
