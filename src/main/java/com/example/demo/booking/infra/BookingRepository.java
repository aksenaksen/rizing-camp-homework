package com.example.demo.booking.infra;

import com.example.demo.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking entity);

    Optional<Booking> findById(Long id);

    void deleteById(Long id);
}
