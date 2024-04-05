package com.his.his.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.dto.EmployeeRequestDto;
import com.his.his.dto.PatientRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Patient;
import com.his.his.models.Patient.PatientType;
import com.his.his.models.Patient_Doctor;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.Patient_DoctorId;
import com.his.his.repository.PatientRepository;
import com.his.his.repository.Patient_DoctorRepository;
import com.his.his.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Patient_DoctorService {
    @Autowired
    private Patient_DoctorRepository patientDoctorRepository;

    @Autowired
    private final UserRepository employeeRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public Patient_DoctorService(Patient_DoctorRepository patientDoctorRepository, UserRepository employeeRepository,
            PatientRepository patientRepository) {
        this.patientDoctorRepository = patientDoctorRepository;
        this.employeeRepository = employeeRepository;
        this.patientRepository = patientRepository;
    }

    public Patient_Doctor addPatient_Doctor(UUID patientId, UUID doctorId) {
        // Check if the patient and doctor exist
        User doctor = employeeRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id " + patientId));

        // Check if the relationship already exists
        Patient_DoctorId id = new Patient_DoctorId(patientId, doctorId);
        if (patientDoctorRepository.existsById(id)) {
            throw new IllegalArgumentException("The relationship between the patient and the doctor already exists");
        }

        // Create a new relationship and save it
        Patient_Doctor patientDoctor = new Patient_Doctor();
        patientDoctor.setId(id);
        patientDoctor.setPatient(patient);
        patientDoctor.setDoctor(doctor);
        return patientDoctorRepository.save(patientDoctor);
    }

    public User getDoctorById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id " + id));
    }

    public ResponseEntity<?> getAllPatientsByDoctorID(UUID doctorId) {
        User doctor = getDoctorById(doctorId);
        List<Patient_Doctor> patient_Doctor = patientDoctorRepository.findPatientsByDoctor(doctor);

        List<Patient> patients = patient_Doctor.stream().map(Patient_Doctor::getPatient).toList();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    patients.stream()
                            .map(patient -> {
                                PatientRequestDto dto = patientService.convertPatientToDto(patient);
                                return dto;
                            }).collect(Collectors.toList()));
        }
    }

    public ResponseEntity<List<EmployeeRequestDto>> getAllDoctorsByPatientID(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id " + patientId));
        List<Patient_Doctor> patient_Doctor = patientDoctorRepository.findDoctorsByPatient(patient);

        List<EmployeeRequestDto> doctors = patient_Doctor.stream().map(Patient_Doctor::getDoctor)
                .map(doctor -> {
                    EmployeeRequestDto dto = employeeService.convertEmployeeToDto(doctor);
                    return dto;
                }).toList();

        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(doctors);
        }
    }

    public ResponseEntity<?> getAllInpatientsByDoctorID(UUID doctorId) {
        // TODO: DOCTOR ID CHECK KRNI HAI
        List<Patient_Doctor> patient_Doctor = patientDoctorRepository.findAll();
        List<Patient> patients = patient_Doctor.stream()
                .map(Patient_Doctor::getPatient)
                .filter(patient -> patient.getPatientType() == PatientType.INPATIENT).toList();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    patients.stream()
                            .map(patient -> {
                                PatientRequestDto dto = patientService.convertPatientToDto(patient);
                                return dto;
                            }).collect(Collectors.toList()));
        }
    }

    public ResponseEntity<?> getAllOutpatientsByDoctorID(UUID doctorId) {
        List<Patient_Doctor> patient_Doctor = patientDoctorRepository.findAll();
        List<Patient> patients = patient_Doctor.stream()
                .map(Patient_Doctor::getPatient).filter(patient -> patient.getPatientType() == PatientType.OUTPATIENT)
                .toList();
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    patients.stream()
                            .map(patient -> {
                                PatientRequestDto dto = patientService.convertPatientToDto(patient);
                                return dto;
                            }).collect(Collectors.toList()));
        }
    }

}
