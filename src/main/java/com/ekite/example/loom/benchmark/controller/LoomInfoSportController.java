package com.ekite.example.loom.benchmark.controller;

import com.ekite.example.loom.benchmark.service.loom.service.LoomInfoSportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/loomInfoSport")
public class LoomInfoSportController {

    @Autowired
    LoomInfoSportService loomInfoSportService;

    @GetMapping("/info")
    public void findInfoSport() throws InterruptedException {
        loomInfoSportService.findInfoSport();
    }
}
