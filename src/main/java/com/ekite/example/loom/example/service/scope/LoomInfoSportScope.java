package com.ekite.example.loom.example.service.scope;

import com.ekite.example.loom.example.dto.InfoSport;
import com.ekite.example.loom.example.dto.InfoSportComponent;
import com.ekite.example.loom.example.dto.SportLesson;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.util.concurrent.Future;

public class LoomInfoSportScope extends StructuredTaskScope<InfoSportComponent> {

    private volatile SportLesson sportLesson;

    @Override
    protected void handleComplete(Future<InfoSportComponent> infoSportFuture) {
        switch (infoSportFuture.state()) {
            case RUNNING -> throw new IllegalStateException("Currently running...");
            case SUCCESS -> {
                switch (infoSportFuture.resultNow()) {
                    case SportLesson sportLesson -> this.sportLesson = sportLesson;
                }
            }
            case FAILED -> throw new RuntimeException(infoSportFuture.exceptionNow());
            case CANCELLED -> {}
        }
    }

    public InfoSport getInfoSport() {
        if (this.sportLesson != null) {
            return new InfoSport(this.sportLesson);
        } else {
            throw new RuntimeException();
        }
    }

}
