package com.his.his.contoller;

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

import com.his.his.services.Patient_DoctorService;
import com.his.his.services.PublicPrivateService;

@RestController
@CrossOrigin("*")
@RequestMapping("/patientDoctor")
public class Patient_DoctorController {

    @Autowired
    private Patient_DoctorService patientDoctorService;

    @Autowired
    private PublicPrivateService publicPrivateService;
    
    // @Autowired
    // private EmployeeService employeeService;

    @GetMapping("/getAllPatientsByDoctorID/{doctorId}")
    @PreAuthorize("hasAuthority('patient:read')")
    public ResponseEntity<?> getAllPatientsByDoctorID(@PathVariable String doctorId) {
        try {
            UUID privateDoctorId = publicPrivateService.privateIdByPublicId(doctorId);
            return patientDoctorService.getAllPatientsByDoctorID(privateDoctorId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Doctor not found with id = "+doctorId, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("getAllDoctorsByPatientID/{patientId}")
    @PreAuthorize("hasAuthority('desk:read')")
    public ResponseEntity<?> getAllDoctorsByPatientID(@PathVariable String patientId) {
        try {
            UUID privatePatientId = publicPrivateService.privateIdByPublicId(patientId);
            return patientDoctorService.getAllDoctorsByPatientID(privatePatientId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+patientId, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAllInpatientsByDoctorID/{doctorId}")
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<?> getAllInpatientsByDoctorID(@PathVariable String doctorId) {
        try {
            UUID privateDoctorId = publicPrivateService.privateIdByPublicId(doctorId);
            return patientDoctorService.getAllInpatientsByDoctorID(privateDoctorId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Doctor not found with id = "+doctorId, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getAllOutpatientsByDoctorID/{doctorId}")
    @PreAuthorize("hasAuthority('doctor:read')")
    public ResponseEntity<?> getAllOutpatientsByDoctorID(@PathVariable String doctorId) {
        try {
            UUID privateDoctorId = publicPrivateService.privateIdByPublicId(doctorId);
            return patientDoctorService.getAllOutpatientsByDoctorID(privateDoctorId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Doctor not found with id = "+doctorId, HttpStatus.NOT_FOUND);
        }
    }
}
