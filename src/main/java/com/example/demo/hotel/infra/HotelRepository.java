package com.example.demo.hotel.infra;

import com.example.demo.hotel.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
    SELECT DISTINCT h
    FROM Hotel h
    LEFT JOIN FETCH h.roomTypes rt
    WHERE h.id = :hotelId
    ORDER BY rt.onPrice ASC
""")
    Optional<Hotel> findHotelWithRoomTypesWithOnPrice(@Param("hotelId") Long hotelId);

    @Query("""
    SELECT DISTINCT h
    FROM Hotel h
    LEFT JOIN FETCH h.roomTypes rt
    WHERE h.id = :hotelId
    ORDER BY rt.offPrice ASC
""")
    Optional<Hotel> findHotelWithRoomTypesWithOffPrice(@Param("hotelId") Long hotelId);

    @Query("""
    SELECT DISTINCT h
    FROM Hotel h
    LEFT JOIN FETCH h.roomTypes rt
    ORDER BY rt.onPrice ASC
""")
    List<Hotel> findAllWithRoomTypes();

    @Query("SELECT rt.hotel FROM RoomType rt WHERE rt.id = :roomTypeId")
    Optional<Hotel> findHotelByRoomTypeId(@Param("roomTypeId") Long roomTypeId);
}
