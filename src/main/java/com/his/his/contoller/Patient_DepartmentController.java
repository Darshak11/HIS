package com.his.his.contoller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Patient>> getAllPatientsByDepartmentID(@PathVariable UUID departmentId) {
        return patientDepartmentService.getAllPatientsByDepartmentID(departmentId);
    }

    @GetMapping("/getAllDepartmentsByPatientID/{patientId}")
    @PreAuthorize("hasAuthority('department:read')")
    public ResponseEntity<List<DepartmentRequestDto>> getAllDepartmentsByPatientID(@PathVariable UUID patientId) {
        return patientDepartmentService.getAllDepartmentsByPatientID(patientId);
    }
}
