package com.example.cropmanagement.Controllers;

import com.example.cropmanagement.DTOs.CropDto;
import com.example.cropmanagement.DTOs.FarmerDto;
import com.example.cropmanagement.Service.CropService;
import com.example.cropmanagement.Service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crop")

public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }


    @GetMapping()
    public ResponseEntity<?> allCrops(){
        return cropService.allCrops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCropById(@PathVariable Long id){
        return cropService.getCrop(id);
    }

    @PostMapping()
    public ResponseEntity<?> addCrop(@RequestBody CropDto dto){
        return cropService.addOrUpdateCrop(dto);
    }

    @PutMapping()
    public ResponseEntity<?> updateFarmer(@RequestBody CropDto dto){
        return cropService.addOrUpdateCrop(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrop(@PathVariable Long id){
        return cropService.deleteCrop(id);
    }
}
