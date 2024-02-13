package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientDetailsServiceImpl patientService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody PatientRegisterDto registerDto) {
        return patientService.signup(registerDto);
    }
}
