package com.his.his.services;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.dto.PatientRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Patient;
import com.his.his.models.Patient.PatientType;
import com.his.his.repository.PatientRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
// @RequiredArgsConstructor
public class PatientService {
    
    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private PublicPrivateService publicPrivateService;

    @Autowired 
    private EmailService emailService;

    @Autowired
    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public ResponseEntity<?> signup(PatientRegisterDto patientRegisterDto){
        //Create patient object from DTO
        Patient patient = new Patient();
        patient.setName(patientRegisterDto.getName());
        patient.setAabhaId(patientRegisterDto.getAabhaId());
        // patient.setAadharId(patientRegisterDto.getAadharId());
        patient.setEmailId(patientRegisterDto.getEmailId());
        patient.setDateOfBirth(patientRegisterDto.getDateOfBirth());
        patient.setEmergencyContactNumber(patientRegisterDto.getEmergencyContactNumber());
        patient.setGender(patientRegisterDto.getGender());
        patient.setPatientType(PatientType.NOT_VERIFIED);
        patient.setBloodGroup(patientRegisterDto.getBloodGroup());
        //Put this in Exception block for handling failure
        patientRepository.save(patient);
        publicPrivateService.savePublicPrivateId(patient.getPatientId(), "PATIENT");
        try {
            emailService.sendHtmlEmail(patient.getEmailId(), "You have been successfully registered into our system","TermsAndConditions.html",publicPrivateService.publicIdByPrivateId(patient.getPatientId()), patientRegisterDto.getPatientType());
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity<>("Patient Registered Successfully",HttpStatus.OK);
    }

    public void createPatient(Patient patient){
        // try {
        //     emailService.sendHtmlEmail(patient.getEmailId(), "You have been successfully registered into our system","TermsAndConditions.html");
        // } catch (MessagingException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        patient = patientRepository.save(patient);
        publicPrivateService.savePublicPrivateId(patient.getPatientId(), "PATIENT");
        return;
    }

    public PatientRequestDto convertPatientToDto(Patient patient){
        PatientRequestDto dto = new PatientRequestDto();
        dto.setPatientId(publicPrivateService.publicIdByPrivateId(patient.getPatientId()));
        dto.setName(patient.getName());
        dto.setAabhaId(patient.getAabhaId());
        // dto.setAadharId(patient.getAadharId());
        dto.setEmailId(patient.getEmailId());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setEmergencyContactNumber(patient.getEmergencyContactNumber());
        dto.setGender(patient.getGender());
        dto.setPatientType(patient.getPatientType());
        dto.setDischargeStatus(patient.getDischargeStatus());
        dto.setBloodGroup(patient.getBloodGroup());
        return dto;
    }

    public List<PatientRequestDto> getAllPatients(){
        return patientRepository.findAll().stream()
        .map(patient ->{
            PatientRequestDto dto = convertPatientToDto(patient);
            return dto;
        }).collect(Collectors.toList());
    } 

    public List<PatientRequestDto> getAllInpatients() {
        return patientRepository.findByPatientType(PatientType.INPATIENT).stream()
        .map(patient ->{
            PatientRequestDto dto = convertPatientToDto(patient);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<PatientRequestDto> getAllOutpatients() {
        return patientRepository.findByPatientType(PatientType.OUTPATIENT).stream()
        .map(patient ->{
            PatientRequestDto dto = convertPatientToDto(patient);
            return dto;
        }).collect(Collectors.toList());
    }

    public PatientRequestDto getPatientId(UUID id){
        Optional <Patient> patientOptional=patientRepository.findById(id);
        if(!patientOptional.isPresent()){
            throw new ResourceNotFoundException("Patient not found with id = "+id);
        }
        return convertPatientToDto(patientOptional.get());
    }

    public PatientRequestDto updatePatient(UUID id,PatientRegisterDto patientDetails){
        Patient updatePatient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient does not exist with id "+id));
        updatePatient.setName(patientDetails.getName());
        updatePatient.setAabhaId(patientDetails.getAabhaId());
        // updatePatient.setAadharId(patientDetails.getAadharId());
        updatePatient.setEmailId(patientDetails.getEmailId());
        updatePatient.setDateOfBirth(patientDetails.getDateOfBirth());
        updatePatient.setEmergencyContactNumber(patientDetails.getEmergencyContactNumber());
        updatePatient.setGender(patientDetails.getGender());
        updatePatient.setPatientType(patientDetails.getPatientType());
        updatePatient.setBloodGroup((patientDetails.getBloodGroup()));
        Patient updatedPatient = patientRepository.save(updatePatient);
        return getPatientId(id);
    }

    public PatientRequestDto transferPatient(UUID id,Patient.PatientType newPatientType){
        Patient patient=patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id "+id)) ;
        patient.setPatientType(newPatientType);
        patientRepository.save(patient);
        return convertPatientToDto(patient);
    }

    public List<String> getPatientIdByName(String patientName) {
        List<String> patientIds = new ArrayList<>();
        List<Patient> patients = patientRepository.findAll();//.findByName(patientName);
        
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                if(patient.getName().equals(patientName))
                    patientIds.add(publicPrivateService.publicIdByPrivateId(patient.getPatientId()));
            }
            return patientIds;
        } else {
            throw new ResourceNotFoundException("Patients with name " + patientName + " not found");
        }
    }

    public void verifyEmail(String publicId, String patientType) {
        UUID privatePatientId = publicPrivateService.privateIdByPublicId(publicId);
        Patient.PatientType type = Patient.PatientType.valueOf(patientType);
        Patient patient = patientRepository.findById(privatePatientId).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id = " + publicId));
        patient.setPatientType(type);
        patientRepository.save(patient);
    }
}
