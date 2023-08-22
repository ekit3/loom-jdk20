package com.ekite.example.loom.example.service.scope;

import com.ekite.example.loom.example.dto.SportLesson;
import jdk.incubator.concurrent.StructuredTaskScope;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

public class LoomSportLessonScope extends StructuredTaskScope<SportLesson> {

    private final Collection<SportLesson> sportLessons = new ConcurrentLinkedQueue<>();
    private final Collection<Throwable> exceptions = new ConcurrentLinkedQueue<>();

    @Override
    protected void handleComplete(Future<SportLesson> courseFuture) {
        switch (courseFuture.state()) {
            case RUNNING -> throw new IllegalStateException("Currently running...");
            case SUCCESS -> this.sportLessons.add(courseFuture.resultNow());
            case FAILED -> this.exceptions.add(courseFuture.exceptionNow());
            case CANCELLED -> {}
        }
    }

    public SportLesson nextSportLessonAvailable() throws Exception {
        return this.sportLessons.stream()
                .min(Comparator.comparing(SportLesson::nextLesson))
                .orElseThrow(this::exception);
    }

    public Exception exception() {
        Exception exception = new Exception();
        this.exceptions.forEach(exception::addSuppressed);
        return exception;
    }

    public static SportLesson getSportLessonFromLille() {
        return new SportLesson("Gym", LocalDateTime.parse("2023-06-08T10:00:00.000"), "Lille");
    }

    public static SportLesson getSportLessonFromLesquin() {
        return new SportLesson("Cycling", LocalDateTime.parse("2023-06-08T11:00:00.000"), "Lesquin");
    }

    public static SportLesson getSportLessonFromMarcq() {
        return new SportLesson("Lift", LocalDateTime.parse("2023-06-08T12:00:00.000"), "Marcq");
    }
}
