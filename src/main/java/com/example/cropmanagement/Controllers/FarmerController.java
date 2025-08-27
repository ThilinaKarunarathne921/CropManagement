package com.example.cropmanagement.Controllers;

import com.example.cropmanagement.DTOs.FarmerDto;
import com.example.cropmanagement.Service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmer")

public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @GetMapping()
    public ResponseEntity<?> allFarmers(){
        return farmerService.allFarmers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFarmerById(@PathVariable Long id){
        return farmerService.getFarmerById(id);
    }

    @PostMapping()
    public ResponseEntity<?> addFarmer(@RequestBody FarmerDto dto){
        return farmerService.addFarmer(dto);
    }

    @PutMapping()
    public ResponseEntity<?> updateFarmer(@RequestBody FarmerDto dto){
        return farmerService.updateFarmer(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFarmer(@PathVariable Long id){
        return farmerService.deleteFarmer(id);
    }


}
