package com.example.studyRoomsINPT.service;

import com.example.studyRoomsINPT.model.Room;

import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    void deleteRoom(Long roomId);
    List<Room> getAllRooms();
    Room getRoomById(Long roomId);

    List<Room> searchRooms(String searchTerm);

    Room getRoomByNumber(String roomNumber);
}
