package com.example.demo.booking.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    CREATED("예약생성"),
    PURCHASED("결제완료"),
    CANCELED("예약취소"),
    REFUNDED("환불완료"),
    END("숙박완료");

    private final String description;
}
