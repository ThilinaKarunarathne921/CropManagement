package com.example.cropmanagement.Service;


import com.example.cropmanagement.DTOs.FieldDto;
import com.example.cropmanagement.Mappers.FieldMapper;
import com.example.cropmanagement.Models.CropModel;
import com.example.cropmanagement.Models.FieldModel;
import com.example.cropmanagement.Repositories.CropRepository;
import com.example.cropmanagement.Repositories.FieldRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final CropRepository cropRepositor;
    private final FieldMapper fieldMapper;

    public FieldService(FieldRepository fieldRepository, CropRepository cropRepositor, FieldMapper fieldMapper) {
        this.fieldRepository = fieldRepository;
        this.cropRepositor = cropRepositor;
        this.fieldMapper = fieldMapper;
    }

    public ResponseEntity<?> allFields() {
        List<FieldModel> all = fieldRepository.findAll();
        if (all.isEmpty()) {   // List is empty
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("No Items to show");
        }

        List<FieldDto> list = all.stream()
                .map(fieldMapper::toDto) // convert each model to DTO
                .toList();

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<?> getField(Long id) {
        Optional<FieldModel> model = fieldRepository.findById(id); //get the record
        if(model.isEmpty()){ //no record found
            throw new IllegalArgumentException("No record found for Id "+id);
        }

        FieldDto dto = fieldMapper.toDto(model.get()); // convert to dto
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> addField(FieldDto dto) {
        FieldModel model = fieldMapper.toModel(dto);  //convert to dto ( has validators inside )

        FieldModel savedModel = fieldRepository.save(model);  //save and get saved record
        FieldDto newDto = fieldMapper.toDto(savedModel);  //convert to dto

        return ResponseEntity.ok(newDto);
    }

    public ResponseEntity<?> updateField(FieldDto dto) {
        FieldModel model = fieldMapper.toModel(dto);  //convert to dto ( has validators inside )

        FieldModel updated = fieldRepository.save(model);
        FieldDto updatedDto = fieldMapper.toDto(updated);

        return ResponseEntity.ok(updatedDto);
    }

    public ResponseEntity<?> deleteField(Long id) {
        Optional<FieldModel> model = fieldRepository.findById(id);  //get the record
        if(model.isEmpty()){  // no record found
            throw new IllegalArgumentException("No record found for Field Id "+ id);
        }

        fieldRepository.delete(model.get());  //delete

        return ResponseEntity.ok("Item Deleted Successfully...");
    }

    public ResponseEntity<?> setCrop(Long fieldId, Long cropId) {
        Optional<FieldModel> field = fieldRepository.findById(fieldId);  // get the field record of the id
        if(field.isEmpty()){  // no field with that id
            throw new IllegalArgumentException("Field record with id : "+ fieldId+ " not exist");
        }
        FieldModel fieldRecord = field.get();  // set fieldRecord as field

        if(cropId!=null){  // has a crop id in params ( means update or set crop to field )
            Optional<CropModel> crop = cropRepositor.findById(cropId);  // get crop record
            if (crop.isEmpty()) {   // no record with that crop id
                throw new IllegalArgumentException("Crop record with id : " + cropId + " not exist");
            }
            CropModel cropRecord = crop.get();  //set CropRecord as crop

            fieldRecord.setCurrentCrop(cropRecord);  // set crop as current crop
            fieldRecord.setStartedDate(LocalDateTime.now());  //set Start date as now
            fieldRecord.setHarvestDate(fieldRecord.getStartedDate().plusDays(cropRecord.getDuration()));  // calculate and set harvest date ( Start date + Crop.Duration = Harvest date)
        }
        else{  // cropId == null ( means for remove the crop from field )
            fieldRecord.setCurrentCrop(null);
            fieldRecord.setStartedDate(null);
            fieldRecord.setHarvestDate(null);
        }

        FieldModel updated = fieldRepository.save(fieldRecord);  // update field record
        FieldDto dto = fieldMapper.toDto(updated);  // convert updated record dto

        return ResponseEntity.ok(dto);

    }
}
