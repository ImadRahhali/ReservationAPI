package com.example.studyRoomsINPT.model;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private LocalTime startHour;

    private LocalTime endHour;

    private LocalDate reservationDate;

    // Other fields and methods

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}