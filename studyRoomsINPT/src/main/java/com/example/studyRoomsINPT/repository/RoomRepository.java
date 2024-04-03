package com.example.studyRoomsINPT.repository;

import com.example.studyRoomsINPT.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long id);

    @Query("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber")
    Optional<Room> findByRoomNumber(String roomNumber);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT res.room.id FROM Reservation res WHERE (" +
            "(res.reservationDate = :reservationDate) AND " +
            "(res.startHour <= :endHour AND res.endHour >= :startHour)" +
            ")" +
            ") AND r.isOccupied = false"
    )
    List<Room> findAvailableRooms(
            @Param("reservationDate") LocalDate reservationDate,
            @Param("startHour") LocalTime startHour,
            @Param("endHour") LocalTime endHour
    );
    @Query("SELECT r FROM Room r WHERE r.isOccupied = true")
    List<Room> findCurrentlyOccupiedRooms();
    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.isOccupied = true WHERE r.id = :roomId")
    void markRoomAsOccupied(@Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.isOccupied = false WHERE r.id = :roomId")
    void markRoomAsAvailable(@Param("roomId") Long roomId);
}
