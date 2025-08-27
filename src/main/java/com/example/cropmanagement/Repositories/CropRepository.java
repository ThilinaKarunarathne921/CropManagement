package com.example.cropmanagement.Repositories;

import com.example.cropmanagement.Models.CropModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CropRepository extends JpaRepository<CropModel,Long> {

    Optional<CropModel> findByName(String name);

    Optional<CropModel> findByNameAndVariety(String name, String variety);
}
