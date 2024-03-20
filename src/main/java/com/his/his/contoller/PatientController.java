package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.models.Patient;
import com.his.his.repository.PatientRepository;
import com.his.his.services.PatientService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('desk:create')")
    public ResponseEntity<?> signup(@RequestBody PatientRegisterDto registerDto) {
            return patientService.signup(registerDto);
        }

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('desk:read')")
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    // //BUILD CREATE Patient REST API
    // @PostMapping
    // public Patient createPatient(@RequestBody Patient patient){
    //     // logger.debug("Patient added");
    //     return patientRepository.save(patient);
    // }
    
    //BUILD GET Patient BY ID REST API
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('desk:read')")
    public ResponseEntity<Patient> getPatientId(@PathVariable UUID id){
        Patient patient = patientService.getPatientId(id);
        // logger.debug("Patient found");
        return ResponseEntity.ok(patient);
    }

    // //BUILD UPDATE Patient REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('desk:update')")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<Patient> updatePatient(@PathVariable UUID id,@RequestBody Patient patientDetails){
        Patient updatedPatient = patientService.updatePatient(id, patientDetails);
        patientRepository.save(updatedPatient);
        // logger.debug("Successfully updated the Patient");
        return ResponseEntity.ok(updatedPatient);
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
    @PreAuthorize("hasAuthority('desk:update')")
    public ResponseEntity<Patient> transferPatient(@PathVariable UUID id,@RequestParam Patient.PatientType newPatientType){
        Patient patient = patientService.transferPatient(id, newPatientType);
        // logger.debug("Patient found");
        return ResponseEntity.ok(patient);
    }

    
    @GetMapping("/getPatientId")
    @PreAuthorize("hasAuthority('desk:read')")
    public ResponseEntity<List<UUID>> getPatientIdByName(@RequestParam String patientName) {
        return ResponseEntity.ok(patientService.getPatientIdByName(patientName));
    }
    
}
