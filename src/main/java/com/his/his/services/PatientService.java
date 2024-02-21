package com.his.his.services;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.models.Patient;
import com.his.his.repository.PatientRepository;
import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
// @RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> signup(PatientRegisterDto patientRegisterDto){
        //Create patient object from DTO
        Patient patient = new Patient();
        patient.setName(patientRegisterDto.getName());
        patient.setAabhaId(patientRegisterDto.getAabhaId());
        patient.setAadharId(patientRegisterDto.getAadharId());
        patient.setEmailId(patientRegisterDto.getEmailId());
        patient.setDateOfBirth(patientRegisterDto.getDateOfBirth());
        patient.setEmergencyContactNumber(patientRegisterDto.getEmergencyContactNumber());
        patient.setGender(patientRegisterDto.getGender());
        patient.setPatientType(patientRegisterDto.getPatientType());

        //Put this in Exception block for handling failure
        patientRepository.save(patient);

        return new ResponseEntity<>("Patient Registered Successfully",HttpStatus.OK);
    }
}
