package com.example.demo.hotel.application;

import com.example.demo.hotel.domain.Room;
import com.example.demo.hotel.infra.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {
    
    private final RoomRepository roomRepository;
    
    public boolean isRoomAvailable(Long roomId, LocalDate date) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room Not Available"));
        return room.isAvailable(date);
    }

    public boolean isRoomTypeAvailable(Long hotelId, Long roomTypeId, LocalDate date) {
        List<Room> rooms = findRoomsWithVacanciesForAvailability(hotelId, roomTypeId);
        return rooms.stream()
                .anyMatch(room -> room.isAvailable(date));
    }

    private List<Room> findRoomsWithVacanciesForAvailability(Long hotelId, Long roomTypeId) {
        List<Room> rooms = roomRepository.findByHotelIdAndRoomTypeId(hotelId, roomTypeId);
        return loadVacanciesForRooms(rooms);
    }
    
    private List<Room> loadVacanciesForRooms(List<Room> rooms) {
        if (rooms.isEmpty()) {
            return rooms;
        }
        List<Long> roomIds = rooms.stream().map(Room::getId).toList();
        return roomRepository.findByIdsWithVacancies(roomIds);
    }
    
}
