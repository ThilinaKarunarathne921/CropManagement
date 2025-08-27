package com.example.cropmanagement.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cropmanagement.Models.FarmerModel;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<FarmerModel,Long> {
    Optional<FarmerModel> findByName(String name);
}
