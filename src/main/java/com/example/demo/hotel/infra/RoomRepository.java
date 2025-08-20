package com.example.demo.hotel.infra;

import com.example.demo.hotel.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId);

    @Query("""
    SELECT r
    FROM Room r
    LEFT JOIN FETCH r.vacancies 
    WHERE r.id IN :roomIds
    """)
    List<Room> findByIdsWithVacancies(@Param("roomIds") List<Long> roomIds);
}