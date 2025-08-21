package com.example.demo.hotel.presentation;

import com.example.demo.hotel.application.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/{hotelId}")
    public ResponseEntity<ResponseHotelDetail> getHotelDetail(
            @PathVariable Long hotelId,
            @RequestParam(name = "date") LocalDate date
            )
    {
        ResponseHotelDetail res = hotelService.findHotelDetail(hotelId, date);
        return ResponseEntity.ok()
                .body(res);
    }

    @GetMapping()
    public ResponseEntity<List<ResponseHotelRoomType>> getAllRoomType(
            @RequestParam(name = "date") LocalDate date
    ){

        List<ResponseHotelRoomType> res = hotelService.findAllHotelRoomType(date);
        return ResponseEntity.ok()
                .body(res);
    }
}
