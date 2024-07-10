package com.booking.repository;

import com.booking.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    public List<Type> findByTypeName(String typeName);
    
    @Query("SELECT t.typeName, r.roomId, r.roomName, r.numberAdult, r.numberChild, r.discount, t.typeName, t.price FROM Type t LEFT JOIN Room r ON t.typeId = r.type.typeId ORDER BY t.typeName, r.roomId ")
    List<Type> getTypeAndRoom(int typeId);

}
