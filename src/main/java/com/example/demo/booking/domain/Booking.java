package com.example.demo.booking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public void checkDate(){
        LocalDate now = LocalDate.now();
        if(inDate.isBefore(now) || inDate.isEqual(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "당일과 과거의 예약은 취소할 수 없습니다.");
        }
    }

    public void checkUser(Long userId){
        if (!Objects.equals(userId, this.userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "예약 정보 내 사용자와 일치하지 않습니다.");
        }
    }
}
