package com.example.demo.booking.presentation.dto;

import com.example.demo.booking.domain.Booking;
import com.example.demo.booking.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDate inDate;
    private LocalDate outDate;
    private LocalDateTime createdAt;
    private Integer guestNum;
    private BigDecimal price;
    private String requirement;
    private LocalDateTime eta;
    private Boolean isWalkIn;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public static BookingResponseDto from(Booking entity) {
        return new  BookingResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getRoomId(),
                entity.getInDate(),
                entity.getOutDate(),
                entity.getCreatedAt(),
                entity.getGuestNum(),
                entity.getPrice(),
                entity.getRequirement(),
                entity.getEta(),
                entity.getIsWalkIn(),
                entity.getStatus()
        );
    }
}
