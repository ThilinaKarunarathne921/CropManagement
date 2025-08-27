package com.example.cropmanagement.Mappers;

import com.example.cropmanagement.DTOs.CropDto;
import com.example.cropmanagement.Models.CropModel;
import com.example.cropmanagement.Repositories.CropRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CropMapper {
    final CropRepository cropRepository;

    public CropMapper(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public CropDto toDto(CropModel model){
        CropDto dto = new CropDto();

        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setVariety(model.getVariety());
        dto.setDescription(model.getDescription());
        dto.setDuration(model.getDuration());
        dto.setProduction(model.getProduction());
        dto.setStages(model.getStages());

        return dto;
    }

    public CropModel toModel(CropDto dto) {
        CropModel model = new CropModel();

        if(dto.getId()!=null){      // when there is an ID on the dto ( update )
            Optional<CropModel> existing= cropRepository.findById(dto.getId()); //check the current record of that id
            if(existing.isEmpty()){
                throw new IllegalArgumentException("Id " + dto.getId() + " does not exist."); // no current record for that id
            }
            model.setId(dto.getId());   // set id (id is ok)
        }

        Optional<CropModel> similar = cropRepository.findByNameAndVariety(dto.getName(),dto.getVariety());  //check for similar name and variety in records
        if(similar.isPresent() && similar.get().getId()!=dto.getId()){ // found a similar record && this.Id != Old.Id
            throw new IllegalArgumentException("Record With Crop name : "+ dto.getName()+" and variety :"+ dto.getVariety() +" already exist!!!");
        }

        model.setName(dto.getName());
        model.setVariety(dto.getVariety());
        model.setDescription(dto.getDescription());
        model.setDuration(dto.getDuration());
        model.setProduction(dto.getProduction());
        model.setStages(dto.getStages());

        return model;
    }


}
