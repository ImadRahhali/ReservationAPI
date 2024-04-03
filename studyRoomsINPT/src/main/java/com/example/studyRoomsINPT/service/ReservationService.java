package com.example.studyRoomsINPT.service;

import com.example.studyRoomsINPT.dto.ReservationDto;
import com.example.studyRoomsINPT.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationDto createReservation(Reservation reservation,String username);
    List<ReservationDto> getAllReservations();

    Optional<ReservationDto> getReservationById(Long id);

    List<ReservationDto> getReservationsByUserId(Long userId);

    List<ReservationDto> getReservationsByRoomId(Long roomId);

    List<ReservationDto> getReservationsByDate(LocalDate date);

    List<ReservationDto> getReservationsByTime(LocalTime startHour, LocalTime endHour);
    List<ReservationDto> getReservationsByDateAndRoom(LocalDate reservationDate, Long roomId);


    void deleteReservation(Long id);
}
