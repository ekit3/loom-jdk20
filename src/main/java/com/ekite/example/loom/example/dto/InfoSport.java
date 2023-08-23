package com.ekite.example.loom.example.dto;

public record InfoSport(
        SportLesson sportLesson
) {
    public String toString() {
        return sportLesson.toString();
    }
}
