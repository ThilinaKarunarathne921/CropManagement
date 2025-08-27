package com.example.cropmanagement.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CropDto {
    private Long id;
    private String name;
    private String variety;
    private String description;
    private int duration;
    private int production;
    private List<StageVo> stages;
}
/*
json file structure
{
  "id": 1,
  "name": "Wheat",
  "variety": "Durum",
  "description": "High-protein wheat variety",
  "duration": 80,
  "production": 2000,
  "stages": [
    {"order": 1, "name": "Germination", "duration": 10},
    {"order": 2, "name": "Vegetative", "duration": 30}
  ]
}
*/