package com.booking.service;

import com.booking.model.Room;
import com.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> GetAllRooms(){
        return roomRepository.findAll();
    }

    public Room AddRoom(Room room) {

		return roomRepository.save(room);
	}

	public void deleteRoom(Integer roomId) {
		roomRepository.deleteById(roomId);
	}

	public Room RoomByID(Integer roomId) {
		Optional<Room> findById = roomRepository.findById(roomId);
		Room room = findById.get();
		return room;
	}

	public long RoomCount() {
		return roomRepository.count();
	}

	public List<Room> getRoomsByTypeId(int typeId) {
		return roomRepository.findByTypeTypeId(typeId);
	}

}
