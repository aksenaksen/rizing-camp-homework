package com.example.demo.booking.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookingCreateRequestDto {
    private Long roomId;
    private Long userId;
    private LocalDate inDate;
    private LocalDate outDate;

    private Integer guestNum;
    private String requirement;
    private LocalDateTime eta;
    private Boolean isWalkIn;
}
