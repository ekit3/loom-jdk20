package com.ekite.example.loom.benchmark.dto.mmo;

import com.ekite.example.loom.benchmark.service.loom.scope.LoomSportLessonScope;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

public record SportLesson(
        String name,
        LocalDateTime nextLesson,
        String location
) implements InfoSportComponent {

    public static SportLesson getSportLesson() {
        try(var scope = new LoomSportLessonScope()) {
            scope.fork(LoomSportLessonScope::getSportLessonFromLille);
            scope.fork(LoomSportLessonScope::getSportLessonFromLesquin);
            scope.fork(LoomSportLessonScope::getSportLessonFromMarcq);

            scope.join();

            return scope.nextSportLessonAvailable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "Name : " + name + " / Next lesson date : " + nextLesson.toString() + " / Location : " + location;
    }

}
