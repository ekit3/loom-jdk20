package com.ekite.example.loom.example.dto;

import com.ekite.example.loom.example.service.scope.LoomSportLessonScope;

import java.time.LocalDateTime;

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
