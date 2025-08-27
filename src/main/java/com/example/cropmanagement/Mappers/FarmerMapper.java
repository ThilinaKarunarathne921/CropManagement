package com.example.cropmanagement.Mappers;

import com.example.cropmanagement.DTOs.FarmerDto;
import com.example.cropmanagement.Models.FarmerModel;
import com.example.cropmanagement.Repositories.FarmerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FarmerMapper {

    private final FarmerRepository farmerRepository;

    public FarmerMapper(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    // Convert DTO into Model
    public FarmerModel toModel(FarmerDto dto) {
        FarmerModel model = new FarmerModel();

        if (dto.getId() != null) {  // when there is a ID
            Optional<FarmerModel> existing = farmerRepository.findById(dto.getId()); //check the current record of that id
            if (existing.isEmpty()) {
                throw new IllegalArgumentException("Id " + dto.getId() + " does not exist."); // no current record for that id
            }
            model.setId(dto.getId());  //Id is ok
            model.setCreatedAt(existing.get().getCreatedAt()); // preserve createdAt
        }

        model.setName(dto.getName());
        model.setContact(dto.getContact());
        model.setAddress(dto.getAddress());

        return model;
    }

    // Convert Model into DTO
    public FarmerDto toDto(FarmerModel model) {

        FarmerDto dto = new FarmerDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setContact(model.getContact());
        dto.setAddress(model.getAddress());
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}
