package com.example.cropmanagement.Service;

import com.example.cropmanagement.DTOs.CropDto;
import com.example.cropmanagement.Mappers.CropMapper;
import com.example.cropmanagement.Models.CropModel;
import com.example.cropmanagement.Repositories.CropRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CropService {
    private final CropRepository cropRepository;
    private final CropMapper cropMapper;

    public CropService(CropRepository cropRepository, CropMapper cropMapper) {
        this.cropRepository = cropRepository;
        this.cropMapper = cropMapper;
    }

    public ResponseEntity<?> allCrops() {
        List<CropModel> all = cropRepository.findAll();
        if (all.isEmpty()) {   // List is empty
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("No Items to show");
        }

        List<CropDto> list = all.stream() //convert model list in to dto list
                .map(cropMapper::toDto)
                .toList();

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<?> getCrop(Long id) {

        Optional<CropModel> model = cropRepository.findById(id);
        if(model.isEmpty()){     //check the record existence
            throw new IllegalArgumentException("Cannot find a Record for Id " + id );
        }

        CropDto dto = cropMapper.toDto(model.get());  //convert record to dto

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<?> addOrUpdateCrop(CropDto dto) {

       CropModel model = checkDuplicate(dto);  // check for duplicate and return model

        CropModel newModel = cropRepository.save(model); //save to db and get saved model
        CropDto newDto = cropMapper.toDto(newModel);  //convert saved model to dto

        return ResponseEntity.ok(newDto);
    }

    public ResponseEntity<?> deleteCrop(Long id) {
        Optional<CropModel> model = cropRepository.findById(id);
        if(model.isEmpty()){
            throw new IllegalArgumentException("Record with Id "+id+" not found");
        }

        cropRepository.delete(model.get());
        return ResponseEntity.ok("Item deleted successfully.");

    }

    private CropModel checkDuplicate(CropDto dto){
        CropModel model = cropMapper.toModel(dto);  //convert dto to model

        Optional<CropModel> checkDuplicate = cropRepository.findByNameAndVariety(model.getName(),model.getVariety());
        if(checkDuplicate.isPresent() && checkDuplicate.get().getId() != model.getId()){  // record has same name variety but different id (similar other record exist)
            throw new IllegalArgumentException("Variety "+model.getVariety()+" in the Crop Name "+model.getName()+" already exist." );
        }

        return model;
    }

}
