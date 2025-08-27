package com.example.cropmanagement.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDto {
    private Long id;
    private String description;
    private String address;
    private int area;
    private String soilType;
    private String irrigationType;
    private Long farmerId;   // Reference to Farmer

    //optional
    private Long cropId;     // Reference to Crop
    private LocalDateTime startedDate;

    private LocalDateTime harvestDate;  // calculate in toDto file when creating a dto
    private String currentStage;       // calculate in toDto file when creating a dto

}
