package com.booking.repository;

import com.booking.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("select r from Room r")
    Page<Room> pageRoom(Pageable pageable);

    @Query("select r from Room r join Type t on r.type.typeId = t.typeId where r.describeRoom like %?1% or r.roomName like %?1% or r.status like %?1% or t.typeName like %?1% or t.price like %?1%")
    Page<Room> searchRooms(String keyword, Pageable pageable);

    @Query("select r from Room r join Type t on r.type.typeId = t.typeId where r.describeRoom like %?1% or r.roomName like %?1% or r.status like %?1% or t.typeName like %?1% or t.price like %?1%")
    List<Room> searchRoomList(String keyword);

    @Query("SELECT r FROM Room r JOIN Type t ON r.type.typeId = t.typeId WHERE t.typeId = :typeId")
    List<Room> findByTypeTypeId(int typeId);

//    public List<Room> findRoomByRoomName(String roomName);
//
//    public List<Room> findRoomByDescribeRoom(String desRoom);

//    @Query("select r from Room r where r.status_empty = true and r.status_booked = false")
//    List<Room> findByStatus_empty();
}
