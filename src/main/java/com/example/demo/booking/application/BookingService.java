package com.example.demo.booking.application;

import com.example.demo.booking.domain.Booking;
import com.example.demo.booking.domain.BookingStatus;
import com.example.demo.booking.infra.BookingRepository;
import com.example.demo.booking.presentation.dto.*;
import com.example.demo.hotel.application.HotelService;

import com.example.demo.hotel.application.RoomService;
import com.example.demo.hotel.infra.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelService hotelService;
    private final RoomService roomService;

    @Transactional
    public BookingResponseDto save(BookingCreateRequestDto request) {

        // 해당 날짜에 예약 있는지 확인 - hotelService로 해당 기간 vacancy 확인
        LocalDate currentDate = request.getInDate();
        while (currentDate.isBefore(request.getOutDate())) {
            if (!roomService.isRoomAvailable(request.getRoomId(), currentDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 기간에 예약할 수 없는 날짜가 있습니다: " + currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        // 진행
        Booking booking = Booking.create(
                request.getInDate(),
                request.getOutDate(),
                request.getGuestNum(),
                hotelService.getPrice(roomService.getRoomTypeId(request.getRoomId()), request.getInDate(), request.getOutDate()), // 임시 가격
                request.getRequirement(),
                request.getEta(),
                request.getIsWalkIn(),
                request.getUserId(),
                request.getRoomId(),
                BookingStatus.CREATED
        );
        Booking saved = bookingRepository.save(booking);
        // 반환
        return BookingResponseDto.from(saved);
    }

    public void delete(Long id, Long userId) {
        // 예약 존재여부 확인
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "데이터베이스 내 예약 정보가 없습니다. 예약 id: " + id));
        // 에러
        booking.checkDate();
        booking.checkUser(userId);
        // 진행
        bookingRepository.deleteById(id);
    }
}
