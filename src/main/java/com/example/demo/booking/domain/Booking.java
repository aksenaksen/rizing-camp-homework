package com.example.demo.booking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate inDate;

    private LocalDate outDate;

    private LocalDateTime createdAt;

    private Integer guestNum;

    private BigDecimal price;

    private String requirement;

    private LocalDateTime eta;

    private Boolean isWalkIn;

    private Long userId;

    private Long roomId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public static Booking create(LocalDate inDate, LocalDate outDate, Integer guestNum, BigDecimal price, String requirement, LocalDateTime eta, Boolean isWalkIn, Long userId, Long roomId, BookingStatus status) {
        return new Booking(
                null,
                inDate,
                outDate,
                LocalDateTime.now(),
                guestNum,
                price,
                requirement,
                eta,
                isWalkIn,
                userId,
                roomId,
                status
        );
    }
}
