package com.example.demo.hotel.application;

import com.example.demo.hotel.domain.Hotel;
import com.example.demo.hotel.domain.RoomType;
import com.example.demo.hotel.infra.HotelRepository;
import com.example.demo.hotel.presentation.ResponseHotelDetail;
import com.example.demo.hotel.presentation.ResponseHotelRoomType;
import com.example.demo.hotel.presentation.ResponseRoomTypeDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomService roomService;


    @Transactional(readOnly = true)
    public List<ResponseHotelRoomType> findAllHotelRoomType(LocalDate date) {
        List<Hotel> hotels = hotelRepository.findAllWithRoomTypes();

        return hotels.stream()
                .map(hotel -> {
                    RoomType cheapestAvailableRoomType = hotel.getRoomTypes().stream()
                            .filter(roomType -> roomService.isRoomTypeAvailable(hotel.getId(), roomType.getId(), date))
                            .findFirst()
                            .orElse(null);
                    return ResponseHotelRoomType.of(cheapestAvailableRoomType, hotel);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponseHotelDetail findHotelDetail(Long hotelId, LocalDate date) {
        Hotel hotel = hotelRepository.findHotelWithRoomTypes(hotelId)
                .orElseThrow(() -> new RuntimeException("해당하는 호텔이 존재하지 않습니다."));
        
        List<ResponseRoomTypeDetail> roomTypeDetails = hotel.getRoomTypes().stream()
                .map(roomType -> {
                    boolean isAvailable = roomService.isRoomTypeAvailable(hotelId, roomType.getId(), date);
                    return ResponseRoomTypeDetail.of(roomType, date, isAvailable);
                })
                .toList();

        return ResponseHotelDetail.of(hotel, roomTypeDetails);
    }







}
