// package com.his.his.contoller;

// import com.his.his.models.Patient;
// import com.his.his.repository.Patient_DoctorRepository;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// @RestController
// @CrossOrigin("*")
// @RequestMapping("/patient_doctor")
// public class Patient_DoctorController {
//    @Autowired
//    private Patient_DoctorRepository patientDoctorRepository;

// @GetMapping("/getAllPatientsByDoctorID/{doctorId}")
// public ResponseEntity<List<Patient>> getAllPatientsByDoctorID(@PathVariable UUID doctorId) {
//     List<Patient> patients = patientDoctorRepository.findPatientsByDoctorId(doctorId);
//     if (patients.isEmpty()) {
//         return ResponseEntity.noContent().build();
//     } else {
//         return ResponseEntity.ok(patients);
//     }
// }

// //    @GetMapping("getAllDoctorsByPatientID/{patientId}")
// //    public List<UUID> getAllDoctorsByPatientID(@PathVariable UUID patientId) {
// //        // Retrieve doctor IDs associated with the given patientId
// //        List<UUID> doctorIds = patientDoctorRepository.findDoctorIdsByPatientId(patientId);
// //        return doctorIds;
// //    }
// }
