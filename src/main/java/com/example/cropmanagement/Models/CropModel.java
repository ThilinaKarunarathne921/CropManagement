package com.example.cropmanagement.Models;

import com.example.cropmanagement.DTOs.StageVo;
import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "crop")
@Data
@NoArgsConstructor
public class CropModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String variety;

    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int production;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<StageVo> stages;
}
