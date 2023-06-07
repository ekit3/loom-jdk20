package com.ekite.example.loom.benchmark.dto.mmo;

public record InfoSport(
        SportLesson sportLesson
) {
    public String toString() {
        return sportLesson.toString();
    }
}
