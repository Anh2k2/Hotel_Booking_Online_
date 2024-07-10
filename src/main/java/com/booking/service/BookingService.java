package com.booking.service;

import com.booking.model.Booking;
import com.booking.model.Room;
import com.booking.model.User;
import com.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    //add
    public Booking addBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    //getAll
    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    //getById
    public Booking getBookingById(Integer bookId){
        Optional<Booking> findById = bookingRepository.findById(bookId);
        Booking booking = findById.get();
        return booking;
    }

    //count
    public long BookingCount() {
        return bookingRepository.count();
    }

    //delete
    public void deleteBooking(Integer bookId){
        bookingRepository.deleteById(bookId);
    }

    public Booking saveBooking(Booking booking){
        Booking booking1 = new Booking();
        booking1.setBookStatus("WAITING");
        booking1.setCheckIn(new Date());
        return booking1;
    }

    public Booking bookRoom(Booking booking) {
        try {
            // Lưu thông tin đặt phòng vào cơ sở dữ liệu
            Booking savedBooking = bookingRepository.save(booking);

            // Thực hiện các xử lý khác sau khi đặt phòng thành công
            //sendConfirmationEmail(savedBooking);
            //updateRoomAvailability(savedBooking.getRoom());

            return savedBooking;
        } catch (Exception e) {
            // Xử lý lỗi khi đặt phòng
            throw new RuntimeException("Error booking room: " + e.getMessage());
        }
    }

    private void sendConfirmationEmail(Booking booking) {
        // Gửi email xác nhận đặt phòng cho khách hàng
    }

    private void updateRoomAvailability(Room room) {
        // Cập nhật trạng thái phòng sau khi đặt phòng thành công
    }
}
