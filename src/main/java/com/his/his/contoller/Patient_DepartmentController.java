package com.his.his.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.models.Patient;
import com.his.his.services.Patient_DepartmentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/patientDepartment")
public class Patient_DepartmentController {
    @Autowired
    private Patient_DepartmentService patientDepartmentService;

    @GetMapping("/getAllPatientsByDepartmentID/{departmentId}")
    @PreAuthorize("hasAuthority('patient:read')")
    public ResponseEntity<?> getAllPatientsByDepartmentID(@PathVariable UUID departmentId) {
        try {
            return patientDepartmentService.getAllPatientsByDepartmentID(departmentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Department not found with id = "+departmentId.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllDepartmentsByPatientID/{patientId}")
    @PreAuthorize("hasAuthority('desk:read')")
    public ResponseEntity<?> getAllDepartmentsByPatientID(@PathVariable UUID patientId) {
        try {
            return patientDepartmentService.getAllDepartmentsByPatientID(patientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+patientId.toString(), HttpStatus.NOT_FOUND);
        }
    }
}
