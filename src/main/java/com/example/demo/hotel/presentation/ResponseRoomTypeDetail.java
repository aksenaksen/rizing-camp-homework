package com.example.demo.hotel.presentation;

import com.example.demo.hotel.domain.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseRoomTypeDetail(
        Long id,
        String name,
        BigDecimal price,
        Integer capacity,
        String description,
        boolean isAvailable
) {
    public static ResponseRoomTypeDetail of(RoomType roomType, LocalDate date, boolean isAvailable) {
        boolean isPeakSeason = isPeakSeason(date);
        BigDecimal price = isPeakSeason ? roomType.getOnPrice() : roomType.getOffPrice();
        
        return new ResponseRoomTypeDetail(
                roomType.getId(),
                roomType.getName(),
                isAvailable ? price : null,
                roomType.getCapacity(),
                roomType.getDescription(),
                isAvailable
        );
    }
    
    private static boolean isPeakSeason(LocalDate date) {
        int month = date.getMonthValue();
        return month == 12 || month == 1 || month == 2;
    }
}