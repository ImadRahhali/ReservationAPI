package com.example.studyRoomsINPT.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationDto {
    private Long id;
    private String roomNumber;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalDate reservationDate;
}
