package com.ekite.example.loom.benchmark.service.loom.service;

import com.ekite.example.loom.benchmark.dto.mmo.InfoSport;
import com.ekite.example.loom.benchmark.dto.mmo.SportLesson;
import com.ekite.example.loom.benchmark.service.loom.scope.LoomInfoSportScope;
import org.springframework.stereotype.Service;

@Service
public class LoomInfoSportService {

    public void findInfoSport() throws InterruptedException {
        try (var scope = new LoomInfoSportScope()) {

            scope.fork(SportLesson::getSportLesson);

            scope.join();

            InfoSport infoSport = scope.getInfoSport();
            System.out.println("Info sport = " + infoSport.toString());
        }
    }
}
