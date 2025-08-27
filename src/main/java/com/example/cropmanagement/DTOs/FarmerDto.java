package com.example.cropmanagement.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDto {
    private Long id;
    private String name;
    private String contact;
    private String address;
    private LocalDateTime createdAt;
}
