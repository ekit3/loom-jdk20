package com.ekite.example.loom.example.service;

import com.ekite.example.loom.example.dto.InfoSport;
import com.ekite.example.loom.example.dto.SportLesson;
import com.ekite.example.loom.example.service.scope.LoomInfoSportScope;
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
