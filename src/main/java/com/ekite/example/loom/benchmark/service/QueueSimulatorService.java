package com.ekite.example.loom.benchmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
/*
 * Queue management for a customer service, customers submit requests that need to be processed.
 * Each request can be processed independently, allowing for parallelism to improve performance.
 */ public class QueueSimulatorService {


    public List<String> generateRequests(Integer nbRequest) {
        return IntStream.range(0, nbRequest)
                .mapToObj(index -> "Request #" + (index + 1))
                .toList();
    }

    public Flux<String> generateReactiveRequests(Integer nbRequest) {
        return Mono.just(IntStream.range(0, nbRequest)
                        .mapToObj(index -> "Request #" + (index + 1))
                        .toList())
                .flatMapMany(Flux::fromIterable);
    }

    public String processRequest(String request) {
        // Simulate request processing
        String response = "Processed : " + request;
        log.debug(response);
        return response;
    }

}
