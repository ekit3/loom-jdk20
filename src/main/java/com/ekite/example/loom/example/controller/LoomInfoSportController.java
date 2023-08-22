package com.ekite.example.loom.example.controller;

import com.ekite.example.loom.example.service.LoomInfoSportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/loomInfoSport")
public class LoomInfoSportController {

    @Autowired
    LoomInfoSportService loomInfoSportService;

    @GetMapping()
    public void findInfoSport() throws InterruptedException {
        loomInfoSportService.findInfoSport();
    }
}
