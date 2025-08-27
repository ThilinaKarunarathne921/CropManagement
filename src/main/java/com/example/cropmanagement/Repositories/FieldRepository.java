package com.example.cropmanagement.Repositories;

import com.example.cropmanagement.Models.FieldModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<FieldModel,Long> {
}
