package com.his.his.contoller;

import com.his.his.dto.PatientRegisterDto;
import com.his.his.dto.PatientRequestDto;
import com.his.his.models.Patient;
import com.his.his.repository.PatientRepository;
import com.his.his.services.PatientService;
import com.his.his.services.PublicPrivateService;

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

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PublicPrivateService publicPrivateService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('desk:create')")
    public ResponseEntity<?> signup(@RequestBody PatientRegisterDto registerDto) {
            return patientService.signup(registerDto);
        }

    
    @GetMapping
    @PreAuthorize("hasAuthority('desk:read')")
    public List<PatientRequestDto> getAllPatients(){
        return patientService.getAllPatients();
    }

    @GetMapping("/allInpatients")
    @PreAuthorize("hasAuthority('desk:read') or hasAuthority('headNurse:read') or hasAuthority('doctor:read') or hasAuthority('nurse:read')")
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
    public ResponseEntity<?> getPatientId(@PathVariable String id){
        try {
            UUID privatePatientId=publicPrivateService.privateIdByPublicId(id);
            PatientRequestDto patient = patientService.getPatientId(privatePatientId);
            // logger.debug("Patient found");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id, HttpStatus.NOT_FOUND);
        }
    }

    // //BUILD UPDATE Patient REST API
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('desk:update')")
    //post mapping vs put mapping. post used to create a resource and put used to update a resource 
    public ResponseEntity<?> updatePatient(@PathVariable String id,@RequestBody PatientRegisterDto patientDetails){
        try {
            UUID privatePatientId = publicPrivateService.privateIdByPublicId(id);
            PatientRequestDto updatedPatient = patientService.updatePatient(privatePatientId, patientDetails);
            // logger.debug("Successfully updated the Patient");
            return ResponseEntity.ok(updatedPatient);    
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id, HttpStatus.NOT_FOUND);
        }
    }

    //BUILD DELETE Patient REST API
    @DeleteMapping("{publicId}")
    @PreAuthorize("hasAuthority('desk:delete')")
    public ResponseEntity<?> deletePatient(@PathVariable String publicId){
        UUID privateId = publicPrivateService.privateIdByPublicId(publicId);
        try {
            patientService.transferPatient(privateId, Patient.PatientType.NOT_VERIFIED);
            // logger.debug("Delete Patient");
            return ResponseEntity.ok("Patient deleted successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+publicId, HttpStatus.NOT_FOUND);
        }
        

    }


    @GetMapping("/transfer/{id}")
    @PreAuthorize("hasAuthority('desk:update')")
    public ResponseEntity<?> transferPatient(@PathVariable String id,@RequestParam Patient.PatientType newPatientType){
        try {
            UUID privatePatientId = publicPrivateService.privateIdByPublicId(id);
            PatientRequestDto patient = patientService.transferPatient(privatePatientId, newPatientType);
            // logger.debug("Patient found");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Patient not found with id = "+id, HttpStatus.NOT_FOUND);
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

    @GetMapping("/verifyEmail/{publicId}/{patientType}")
    public ResponseEntity<?> verifyEmail(@PathVariable String publicId,@PathVariable String patientType) {
        try {
            patientService.verifyEmail(publicId, patientType);
            return ResponseEntity.ok("Email Verified");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Email not sent", HttpStatus.NOT_FOUND);
        }
    }
    
}
