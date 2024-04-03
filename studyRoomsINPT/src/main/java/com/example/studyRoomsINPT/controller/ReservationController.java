package com.example.studyRoomsINPT.controller;

import com.example.studyRoomsINPT.dto.ReservationDto;
import com.example.studyRoomsINPT.model.Reservation;
import com.example.studyRoomsINPT.model.Room;
import com.example.studyRoomsINPT.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationDto> createReservation(@RequestBody Reservation reservation,@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        ReservationDto newReservationDto = reservationService.createReservation(reservation,username);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReservationDto);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllRooms() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        Optional<ReservationDto> reservationDto = reservationService.getReservationById(id);
        return reservationDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserId(@PathVariable Long userId) {
        List<ReservationDto> reservations = reservationService.getReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByRoomId(@PathVariable Long roomId) {
        List<ReservationDto> reservations = reservationService.getReservationsByRoomId(roomId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ReservationDto>> getReservationsByDate(@PathVariable LocalDate date) {
        List<ReservationDto> reservations = reservationService.getReservationsByDate(date);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/time")
    public ResponseEntity<List<ReservationDto>> getReservationsByTime(@RequestParam LocalTime startHour,
                                                                   @RequestParam LocalTime endHour) {
        List<ReservationDto> reservations = reservationService.getReservationsByTime(startHour, endHour);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/date-and-room")
    public ResponseEntity<List<ReservationDto>> getReservationsByDateAndRoom(@RequestParam LocalDate reservationDate,
                                                                          @RequestParam Long roomId) {
        List<ReservationDto> reservations = reservationService.getReservationsByDateAndRoom(reservationDate, roomId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted successfully.");
    }
}
