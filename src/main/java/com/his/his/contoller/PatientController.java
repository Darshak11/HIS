package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.dto.PatientRequestDto;
import com.his.his.models.Patient;
import com.his.his.repository.PatientRepository;
import com.his.his.services.PatientService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<PatientRequestDto> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/allInpatients")
    @PreAuthorize("hasAuthority('desk:read')")
    public List<PatientRequestDto> getAllInpatients() {
        return patientService.getAllInpatients();
    }

    @GetMapping("/allOutpatients")
    @PreAuthorize("hasAuthority('desk:read')")
    public List<PatientRequestDto> getAllOutpatients() {
        return patientService.getAllOutpatients();
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
    public ResponseEntity<?> getPatientId(@PathVariable UUID id){
        try {
            PatientRequestDto patient = patientService.getPatientId(id);
            // logger.debug("Patient found");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    // //BUILD UPDATE Patient REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('desk:update')")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<?> updatePatient(@PathVariable UUID id,@RequestBody Patient patientDetails){
        try {
            PatientRequestDto updatedPatient = patientService.updatePatient(id, patientDetails);
            // logger.debug("Successfully updated the Patient");
            return ResponseEntity.ok(updatedPatient);    
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id.toString(), HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<?> transferPatient(@PathVariable UUID id,@RequestParam Patient.PatientType newPatientType){
        try {
            PatientRequestDto patient = patientService.transferPatient(id, newPatientType);
            // logger.debug("Patient found");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id.toString(), HttpStatus.NOT_FOUND);
        }
    }

    
    @GetMapping("/getPatientId")
    @PreAuthorize("hasAuthority('desk:read')")
    public ResponseEntity<?> getPatientIdByName(@RequestParam String patientName) {
        try {
            return ResponseEntity.ok(patientService.getPatientIdByName(patientName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with name = "+patientName, HttpStatus.NOT_FOUND);
        }
    }
    
}
