package com.booking.service;

import com.booking.model.Type;
import com.booking.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public Type AddType(Type type){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return typeRepository.save(type);
    }

    public List<Type> GetAllTypes(){
        return typeRepository.findAll();
    }

    public void DeleteTypeById(Integer typeId){
        typeRepository.deleteById(typeId);
    }

    public Type GetTypeById(Integer typeId){
        Optional<Type> findById = typeRepository.findById(typeId);
        Type type = findById.get();
        return type;
    }

    public long GetTypeCount(){
        return typeRepository.count();
    }

    public List<Type> SearchingType(String query){
        List<Type> findByName = typeRepository.findByTypeName(query);
        return findByName;
    }

    public  List<Type> getTypeAndRoom(int typeId){
        List<Type> findType = typeRepository.getTypeAndRoom(typeId);
        return findType;
    }

}
