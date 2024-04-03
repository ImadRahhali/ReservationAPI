package com.example.studyRoomsINPT.service.impl;

import com.example.studyRoomsINPT.dto.ReservationDto;
import com.example.studyRoomsINPT.exception.RoomNotFoundException;
import com.example.studyRoomsINPT.model.Reservation;
import com.example.studyRoomsINPT.model.Room;
import com.example.studyRoomsINPT.model.User;
import com.example.studyRoomsINPT.repository.ReservationRepository;
import com.example.studyRoomsINPT.repository.RoomRepository;
import com.example.studyRoomsINPT.repository.UserRepository;
import com.example.studyRoomsINPT.service.ReservationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDto createReservation(Reservation reservation, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the user for the reservation
        reservation.setUser(user);

        // Save the reservation
        Reservation createdReservation = reservationRepository.save(reservation);

        // Fetch the room along with the reservation
        createdReservation = reservationRepository.findById(createdReservation.getId())
                .orElseThrow(() -> new RuntimeException("Failed to fetch created reservation"));

        // Create and populate the DTO
        ReservationDto dto = new ReservationDto();
        dto.setId(createdReservation.getId());
        dto.setRoomNumber(createdReservation.getRoom().getRoomNumber());
        dto.setStartHour(createdReservation.getStartHour());
        dto.setEndHour(createdReservation.getEndHour());
        dto.setReservationDate(createdReservation.getReservationDate());

        return dto;
    }


    @Override
    public Optional<ReservationDto> getReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(this::convertToDto);
    }

    private ReservationDto convertToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        BeanUtils.copyProperties(reservation, dto);
        dto.setRoomNumber(reservation.getRoom().getRoomNumber()); // Customize room serialization
        return dto;
    }
    @Override
    public List<ReservationDto> getReservationsByUserId(Long userId) {
        User user = new User();
        user.setId(userId);
        List<Reservation> reservations = reservationRepository.findByUser(user);
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByRoomId(Long roomId) {
        // Assuming Reservation has a ManyToOne relationship with Room
        Room room = new Room();
        room.setId(roomId);
        List<Reservation> reservations = reservationRepository.findByRoom(room);
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());    }

    @Override
    public List<ReservationDto> getReservationsByDate(LocalDate date) {
        List<Reservation> reservations = reservationRepository.findByReservationDate(date);
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByTime(LocalTime startHour, LocalTime endHour) {
        List<Reservation> reservations = reservationRepository.findReservationsByTime(startHour, endHour);
        return reservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<ReservationDto> getReservationsByDateAndRoom(LocalDate reservationDate, Long roomId) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            List<Reservation> reservations = reservationRepository.findByReservationDateAndRoom(reservationDate, room);
            return reservations.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            throw new RoomNotFoundException("Room not found");
        }
    }
    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
