package com.example.demo.hotel.presentation;

import com.example.demo.hotel.domain.Hotel;
import com.example.demo.hotel.domain.RoomType;

public record ResponseHotelRoomType(
       Long id,
       String name,
       String description,
       Integer rank,
       float rating,
       ResponseRoomType roomType
) {
    public static ResponseHotelRoomType of(RoomType roomType, Hotel hotel) {
        return new ResponseHotelRoomType(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getRank(),
                hotel.getRating(),
                (roomType != null) ? ResponseRoomType.from(roomType) : null
        );
    }
}
