package com.his.his.services;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Patient;
import com.his.his.models.Patient.PatientType;
import com.his.his.repository.PatientRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        patient.setAge(patientRegisterDto.getAge());

        //Put this in Exception block for handling failure
        patientRepository.save(patient);

        return new ResponseEntity<>("Patient Registered Successfully",HttpStatus.OK);
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    } 

    public List<Patient> getAllInpatients() {
        return patientRepository.findByPatientType(PatientType.INPATIENT);
    }

    public List<Patient> getAllOutpatients() {
        return patientRepository.findByPatientType(PatientType.OUTPATIENT);
    }

    public Patient getPatientId(UUID id){
        Optional <Patient> patientOptional=patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            throw new ResourceNotFoundException("Patient not found with id = "+id);
        }
        return patientOptional.get();
    }

    public Patient updatePatient(UUID id,Patient patientDetails){
        Patient updatePatient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient does not exist with id "+id));
        updatePatient.setName(patientDetails.getName());
        updatePatient.setAabhaId(patientDetails.getAabhaId());
        updatePatient.setAadharId(patientDetails.getAadharId());
        updatePatient.setEmailId(patientDetails.getEmailId());
        updatePatient.setDateOfBirth(patientDetails.getDateOfBirth());
        updatePatient.setEmergencyContactNumber(patientDetails.getEmergencyContactNumber());
        updatePatient.setGender(patientDetails.getGender());
        updatePatient.setPatientType(patientDetails.getPatientType());
        updatePatient.setAge(patientDetails.getAge());
        return updatePatient;
    }

    public Patient transferPatient(UUID id,Patient.PatientType newPatientType){
        Patient patient=patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id "+id)) ;
        patient.setPatientType(newPatientType);
        patientRepository.save(patient);
        return patient;
    }

    public List<UUID> getPatientIdByName(String patientName) {
        List<UUID> patientIds = new ArrayList<>();
        List<Patient> patients = patientRepository.findByName(patientName);
        
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                patientIds.add(patient.getPatientId());
            }
            return patientIds;
        } else {
            throw new ResourceNotFoundException("Patients with name " + patientName + " not found");
        }
    }
}
