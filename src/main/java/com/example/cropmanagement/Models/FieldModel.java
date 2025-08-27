package com.example.cropmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "field")
@Data
@NoArgsConstructor
public class FieldModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int area;

    @Column(nullable = false)
    private String soilType;

    @Column(nullable = false)
    private String irrigationType;

    @Column(nullable = true)
    private LocalDateTime startedDate;

    @Column(nullable = true)
    private LocalDateTime harvestDate;

    @ManyToOne
    @JoinColumn(name="farmer_id")
    private FarmerModel farmer;

    @ManyToOne
    @JoinColumn(nullable = true)
    private CropModel currentCrop;
}
