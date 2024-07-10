package com.booking.repository;

import com.booking.model.Booking;
import com.booking.model.Room;
import com.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {


}
