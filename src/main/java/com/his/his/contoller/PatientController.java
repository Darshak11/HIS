package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Patient;
import com.his.his.repository.PatientRepository;
import com.his.his.services.PatientService;

import java.util.List;
import java.util.UUID;

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
            return patientService.signup(registerDto);
        }

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    // //BUILD CREATE Patient REST API
    // @PostMapping
    // public Patient createPatient(@RequestBody Patient patient){
    //     // logger.debug("Patient added");
    //     return patientRepository.save(patient);
    // }

    //BUILD GET Patient BY ID REST API
    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatientId(@PathVariable UUID id){
        Patient patient=patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id "+id)) ;
        // logger.debug("Patient found");
        return ResponseEntity.ok(patient);
    }

    // //BUILD UPDATE Patient REST API
    @PutMapping("{id}")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id,@RequestBody Patient patientDetails){
        Patient updatePatient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient does not exist with id "+id));
        updatePatient.setName(patientDetails.getName());
        updatePatient.setAabhaId(patientDetails.getAabhaId());
        updatePatient.setAadharId(patientDetails.getAadharId());
        updatePatient.setEmailId(patientDetails.getEmailId());
        updatePatient.setDateOfBirth(patientDetails.getDateOfBirth());
        updatePatient.setEmergencyContactNumber(patientDetails.getEmergencyContactNumber());
        updatePatient.setGender(patientDetails.getGender());
        updatePatient.setPatientType(patientDetails.getPatientType());
        patientRepository.save(updatePatient);
        // logger.debug("Successfully updated the Patient");
        return ResponseEntity.ok(updatePatient);
    }

    // //BUILD DELETE Patient REST API
    // @DeleteMapping("{id}")
    // public ResponseEntity<HttpStatus> deletePatient(@PathVariable long id){
    //     Patient patient = patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not exists "+id));

    //     patientRepository.delete(patient);
    //     // logger.debug("Delete Patient");
    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    // }


    @GetMapping("/transfer/{id}")
    public ResponseEntity<Patient> transferPatient(@PathVariable UUID id,@RequestParam Patient.PatientType newPatientType){
        Patient patient=patientRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id "+id)) ;
        patient.setPatientType(newPatientType);
        patientRepository.save(patient);
        // logger.debug("Patient found");
        return ResponseEntity.ok(patient);
    }

    
}
