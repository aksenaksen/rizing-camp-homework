package com.example.demo.booking.presentation;

import com.example.demo.booking.application.BookingService;
import com.example.demo.booking.presentation.dto.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookingController {
    BookingService bookingService;

    @PostMapping("")
    public ResponseEntity<BookingResponseDto> create(@RequestBody BookingCreateRequestDto request) {

        BookingResponseDto booking = bookingService.save(request);
        return ResponseEntity.ok(booking);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        bookingService.delete(id, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
