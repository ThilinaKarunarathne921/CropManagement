package com.example.cropmanagement.Controllers;


import com.example.cropmanagement.DTOs.CropDto;
import com.example.cropmanagement.DTOs.FieldDto;
import com.example.cropmanagement.Service.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/field")

public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping()
    public ResponseEntity<?> allFields(){
        return fieldService.allFields();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldById(@PathVariable Long id){
        return fieldService.getField(id);
    }

    @PostMapping()
    public ResponseEntity<?> addField(@RequestBody FieldDto dto){
       return fieldService.addField(dto);
    }

    @PutMapping()
    public ResponseEntity<?> updateField(@RequestBody FieldDto dto){
        return fieldService.updateField(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteField(@PathVariable Long id){
       return fieldService.deleteField(id);
    }

    @PatchMapping()
    public ResponseEntity<?> changeCrop(
            @RequestParam Long fieldId,
            @RequestParam(required = false) Long cropId){
        return fieldService.setCrop(fieldId,cropId);
    }

}
