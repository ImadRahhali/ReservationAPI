package com.example.studyRoomsINPT.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    private boolean isOccupied;

    @JsonIgnoreProperties("room")
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private Set<Reservation> reservations = new HashSet<>();

}
