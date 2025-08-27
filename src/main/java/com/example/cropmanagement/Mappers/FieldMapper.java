package com.example.cropmanagement.Mappers;

import com.example.cropmanagement.DTOs.FieldDto;
import com.example.cropmanagement.DTOs.StageVo;
import com.example.cropmanagement.Models.CropModel;
import com.example.cropmanagement.Models.FarmerModel;
import com.example.cropmanagement.Models.FieldModel;
import com.example.cropmanagement.Repositories.CropRepository;
import com.example.cropmanagement.Repositories.FarmerRepository;
import com.example.cropmanagement.Repositories.FieldRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FieldMapper {

    private final FieldRepository fieldRepository;
    private final FarmerRepository farmerRepository;
    private final CropRepository cropRepository;


    public FieldMapper(FieldRepository fieldRepository, FarmerRepository farmerRepository, CropRepository cropRepository) {
        this.fieldRepository = fieldRepository;
        this.farmerRepository = farmerRepository;
        this.cropRepository = cropRepository;
    }


    public FieldModel toModel(FieldDto dto){
        FieldModel model = new FieldModel();

        if (dto.getId() != null) {  // if dto has id ( means Update )
            Optional<FieldModel> existing = fieldRepository.findById(dto.getId());
            if (existing.isEmpty()) { // there is no field record with that id
                throw new IllegalArgumentException("Id " + dto.getId() + " does not exist.");
            }
            model.setId(dto.getId());  // set model id = dto.id
        }

        Optional<FarmerModel> farmerModel = farmerRepository.findById(dto.getFarmerId());  // get farmer record with farmerId
        if(farmerModel.isEmpty()){   // no farmer for farmer id
            throw new IllegalArgumentException("There is no record for farmer id "+dto.getFarmerId());
        }
        model.setFarmer(farmerModel.get());

        model.setDescription(dto.getDescription());
        model.setAddress(dto.getAddress());
        model.setArea(dto.getArea());
        model.setSoilType(dto.getSoilType());
        model.setIrrigationType(dto.getIrrigationType());

        if(dto.getCropId()!=null){  // currently have a Crop assigned
            Optional<CropModel> cropModel = cropRepository.findById(dto.getCropId());  // get the crop record for that crop id

            if(cropModel.isEmpty()){ // there is no crop record for that crop id
                throw new IllegalArgumentException(" Crop record for id "+ dto.getId()+" not exist");
            }
            model.setCurrentCrop(cropModel.get());  //set crop model
            model.setStartedDate(dto.getStartedDate());
            model.setHarvestDate(dto.getHarvestDate());

        }

        return model;
    }


    public FieldDto toDto(FieldModel model){

        FieldDto dto = new FieldDto();

        dto.setId(model.getId());
        dto.setDescription(model.getDescription());
        dto.setAddress(model.getAddress());
        dto.setArea(model.getArea());
        dto.setSoilType(model.getSoilType());
        dto.setIrrigationType(model.getIrrigationType());
        dto.setFarmerId(model.getFarmer().getId());

        if(model.getCurrentCrop()!=null) {  // currently has a crop assigned to the field
            dto.setCropId(model.getCurrentCrop().getId());

            Optional<CropModel> cropModel = cropRepository.findById(dto.getCropId()); // get crop record ( need to calculate stage below
            if (cropModel.isEmpty()) {
                throw new IllegalArgumentException("Crop Cannot be found..");
            }

            dto.setStartedDate(model.getStartedDate());
            dto.setHarvestDate(model.getHarvestDate());

            // --- Calculate current stage ---
            long daysSinceStart = Duration.between(model.getStartedDate(), LocalDateTime.now()).toDays();  // calculate days since start day

            int cumulativeDays = 0;  // to hold cumulative values of stages
            List<StageVo> stages = cropModel.get().getStages();  // get stages from the crop model
            String currentStageName = "Complete";  // current stage

            for (StageVo stage : stages) {                      //loop through stages
                cumulativeDays += stage.getDuration();          // Cumulative Days += stage.duration
                if (daysSinceStart <= cumulativeDays) {         // if days since start <= cumulative day count
                    currentStageName = stage.getName();         // days are between previous stage and end day of this stage
                    break;                                      // set current stage as stage.Name and break
                }
            }
            // Default : days since >= cumulative days count means stages are complete
            dto.setCurrentStage(currentStageName);  // set current stage
        }

        return dto;
    }

}
