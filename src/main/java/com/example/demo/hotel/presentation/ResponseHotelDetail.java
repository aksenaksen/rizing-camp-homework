package com.example.demo.hotel.presentation;

import com.example.demo.hotel.domain.Hotel;

import java.util.List;

public record ResponseHotelDetail(
        Long id,
        String name,
        String description,
        Integer rank,
        float rating,
        List<ResponseRoomTypeDetail> roomTypes
) {

    public static ResponseHotelDetail of(Hotel hotel, List<ResponseRoomTypeDetail> roomTypes) {
        return new ResponseHotelDetail(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getRank(),
                hotel.getRating(),
                roomTypes
        );
    }
}