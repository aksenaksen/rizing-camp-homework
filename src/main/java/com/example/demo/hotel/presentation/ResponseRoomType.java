package com.example.demo.hotel.presentation;

import com.example.demo.hotel.domain.RoomType;

import java.math.BigDecimal;

public record ResponseRoomType(
        Long id,
        String name,
        BigDecimal onPrice,
        BigDecimal offPrice,
        Integer capacity,
        String description
) {
    public static ResponseRoomType from(RoomType roomType) {
        return new ResponseRoomType(
                roomType.getId(),
                roomType.getName(),
                roomType.getOnPrice(),
                roomType.getOffPrice(),
                roomType.getCapacity(),
                roomType.getDescription()
        );
    }
}