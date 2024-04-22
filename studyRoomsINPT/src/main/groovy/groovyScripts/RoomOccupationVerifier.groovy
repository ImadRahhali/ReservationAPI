package groovyScripts

import com.example.studyRoomsINPT.model.Reservation
import com.example.studyRoomsINPT.model.Room
import com.example.studyRoomsINPT.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
public class RoomOccupationVerifier {
    RoomRepository roomRepository

    RoomOccupationVerifier(RoomRepository roomRepository) {
        this.roomRepository = roomRepository
    }

    def verifyRoomOccupation(Reservation reservation) {
        // Get the room for the reservation
        Room room = reservation.room

        // Check if the start hour and end hour are logical for each reservation date
        if (reservation.startHour >= reservation.endHour) {
            throw new IllegalArgumentException("Start hour must be before end hour")
        }

        // Calculate the duration of the reservation
        def duration = reservation.endHour.hour - reservation.startHour.hour

        // Check if the duration is within the valid range (1 hour to whole day)
        if (duration < 1 || duration > 24) {
            throw new IllegalArgumentException("Invalid reservation duration")
        }

        // Get existing reservations for the same interval
        def reservationsInInterval = room.reservations.findAll { existingReservation ->
            existingReservation.reservationDate == reservation.reservationDate &&
                    (reservation.startHour < existingReservation.endHour && reservation.endHour > existingReservation.startHour)
        }

        // Check if the total number of reservations plus the new reservation exceeds the maximum capacity for the interval
        if (reservationsInInterval.size() >= room.maximumCapacity) {
            throw new IllegalArgumentException("Room is at maximum capacity for this interval")
        }

        // Update the number of people in the room after the reservation
        room.setOccupied(true)

        // Save or update the room using the RoomRepository
        roomRepository.save(room)

        // Return true if verification passes
        return true
    }
}
