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

import com.his.his.dto.EmployeeRequestDto;
import com.his.his.models.Patient;
import com.his.his.services.Patient_DoctorService;

@RestController
@CrossOrigin("*")
@RequestMapping("/patientDoctor")
public class Patient_DoctorController {

    @Autowired
    private Patient_DoctorService patientDoctorService;

    // @Autowired
    // private EmployeeService employeeService;

    @GetMapping("/getAllPatientsByDoctorID/{doctorId}")
    @PreAuthorize("hasAuthority('patient:read')")
    public ResponseEntity<List<Patient>> getAllPatientsByDoctorID(@PathVariable UUID doctorId) {
        return patientDoctorService.getAllPatientsByDoctorID(doctorId);
    }


    @GetMapping("getAllDoctorsByPatientID/{patientId}")
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<List<EmployeeRequestDto>> getAllDoctorsByPatientID(@PathVariable UUID patientId) {
    return patientDoctorService.getAllDoctorsByPatientID(patientId);
    }
}
