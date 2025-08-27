package com.example.cropmanagement.Service;


import com.example.cropmanagement.DTOs.FarmerDto;
import com.example.cropmanagement.Mappers.FarmerMapper;
import com.example.cropmanagement.Repositories.FarmerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.cropmanagement.Models.FarmerModel;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final FarmerMapper farmerMapper;

    public FarmerService(FarmerRepository farmerRepository, FarmerMapper farmerMapper) {
        this.farmerRepository = farmerRepository;
        this.farmerMapper = farmerMapper;
    }

    //Get All Farmers List
    public ResponseEntity<?> allFarmers() {
        List<FarmerModel> all = farmerRepository.findAll();
        if (all.isEmpty()) {   // List is empty
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("No Items to show");
        }

        List<FarmerDto> list = all.stream()
                .map(farmerMapper::toDto) // convert each model to DTO
                .toList();


        return ResponseEntity.ok(list);
    }

    //Get Farmer record By ID
    public ResponseEntity<?> getFarmerById(Long id) {
        Optional<FarmerModel> item = farmerRepository.findById(id);  // find the record by id

        if(item.isPresent()){  // record exist
            FarmerDto dto = farmerMapper.toDto(item.get());  // convert model to dto
            return ResponseEntity.ok(dto); // send dto
        }else{   // record of id does not exist
            throw new IllegalArgumentException("Farmer with ID " + id + " not found.");
        }
    }

    //Add a Farmer using DTO
    public ResponseEntity<?> addFarmer(FarmerDto dto) {
        FarmerModel model = farmerMapper.toModel(dto);  // convert dto in to model
        Optional<FarmerModel> item = farmerRepository.findByName(model.getName()); // check for farmer name is available
        if(item.isPresent()){   // same name exist
            throw new IllegalArgumentException("Farmer Name " + model.getName() + " Already Exist.");
        }

        FarmerModel savedItem = farmerRepository.save(model);  // same new record and get the saved record
        FarmerDto  newDto = farmerMapper.toDto(savedItem);  // convert record  to dto
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);  // send dto with response

    }

    //update farmer details
    public ResponseEntity<?> updateFarmer(FarmerDto dto) {
        FarmerModel model = farmerMapper.toModel(dto);   // convert dto in to model
        Optional<FarmerModel> sameName = farmerRepository.findByName(model.getName());  // check same name exist before update
        if(sameName.isPresent() && !Objects.equals(dto.getId(), sameName.get().getId())){  // if same name exist and Id is noe equal
            throw new IllegalArgumentException("Cannot update, Farmer name "+model.getName()+" Already exist");
        }
        FarmerModel updated = farmerRepository.save(model);
        FarmerDto updatedDto = farmerMapper.toDto(updated);
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> deleteFarmer(Long id) {
        Optional<FarmerModel> model = farmerRepository.findById(id);
        if(model.isEmpty()){
            throw new IllegalArgumentException("Record with id "+id+" does not exist");
        }

        farmerRepository.delete(model.get());
        return ResponseEntity.ok("Item deleted successfully.");
    }



}
