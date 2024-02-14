package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody PatientRegisterDto registerDto) {
        //Code to check valid user + Role based access control
        return patientService.signup(registerDto);
    }
}
