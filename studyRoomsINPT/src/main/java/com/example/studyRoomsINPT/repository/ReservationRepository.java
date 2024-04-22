package com.example.studyRoomsINPT.repository;

import com.example.studyRoomsINPT.dto.ReservationDto;
import com.example.studyRoomsINPT.model.Reservation;
import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    List<Reservation> findByRoom(Room room);
    List<Reservation> findByReservationDate(LocalDate reservationDate);

    @Query("SELECT r FROM Reservation r WHERE r.room = :room " +
            "AND ((r.startHour BETWEEN :startHour AND :endHour) OR " +
            "(r.endHour BETWEEN :startHour AND :endHour) OR " +
            "(:startHour BETWEEN r.startHour AND r.endHour) OR " +
            "(:endHour BETWEEN r.startHour AND r.endHour))")
    List<Reservation> findOverlappingReservations(Room room, LocalTime startHour, LocalTime endHour);


    List<Reservation> findByReservationDateAndRoom(LocalDate reservationDate, Room room);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(r.startHour <= :endHour AND r.endHour >= :startHour)")
    List<Reservation> findReservationsByTime(
            @Param("startHour") LocalTime startHour,
            @Param("endHour") LocalTime endHour
    );

}
