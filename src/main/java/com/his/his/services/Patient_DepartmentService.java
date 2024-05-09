package com.his.his.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.his.his.models.*;
import com.his.his.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.dto.PatientRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.CompositePrimaryKeys.Patient_DepartmentId;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Patient_DepartmentService {
    @Autowired
    private final Patient_DepartmentRepository patientDepartmentRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private Employee_DepartmentRepository edRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserRepository userRep;
    @Autowired
    public Patient_DepartmentService(Patient_DepartmentRepository patientDepartmentRepository,
            PatientRepository patientRepository, DepartmentRepository departmentRepository) {
        this.patientDepartmentRepository = patientDepartmentRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    public Patient_Department addPatient_Department(UUID patientId, UUID departmentId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id " + patientId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not exist with id " + departmentId));

        Patient_DepartmentId id = new Patient_DepartmentId(patientId, departmentId);

        if (patientDepartmentRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "The relationship between the patient and the department already exists");
        }

        Patient_Department patientDepartment = new Patient_Department();
        patientDepartment.setId(id);
        patientDepartment.setPatient(patient);
        patientDepartment.setDepartment(department);
        return patientDepartmentRepository.save(patientDepartment);
    }

    public ResponseEntity<?> getAllPatientsByDepartmentID(UUID departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not exist with id " + departmentId));
        List<Patient_Department> patientDepartment = patientDepartmentRepository.findPatientsByDepartment(department);

        List<Patient> patients = patientDepartment.stream().map(Patient_Department::getPatient).filter(patient -> patient.getPatientType() ==Patient.PatientType.INPATIENT).toList();

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

    public ResponseEntity<?> getAllPatientsByNurseID(UUID nurseId) {
        User nurse = userRep.findById(nurseId).orElseThrow();
        Department department = edRepository.findDepartmentsByEmployee(nurse).get(0).getDepartment();//.orElseThrow(() -> new ResourceNotFoundException("Nurse not exist with id " + nurseId)).getDepartment());
        List<Patient_Department> patientDepartment = patientDepartmentRepository.findPatientsByDepartment(department);

        List<Patient> patients = patientDepartment.stream().map(Patient_Department::getPatient).filter(patient -> patient.getPatientType() ==Patient.PatientType.INPATIENT).toList();

        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    patients.stream()
                            .map(patient -> {
                                PatientRequestDto dto = patientService.convertPatientToDto(patient);
                                return dto;
                            })
                            .filter(patient->patient.getPatientType()== Patient.PatientType.INPATIENT).collect(Collectors.toList()));
//                    .collect(Collectors.toList()));
        }
    }

    public ResponseEntity<?> getAllDepartmentsByPatientID(UUID patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id " + patientId));

        List<Patient_Department> patientDepartment = patientDepartmentRepository.findDepartmentsByPatient(patient);

        List<Department> departments = patientDepartment.stream().map(Patient_Department::getDepartment).toList();

        if (departments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    departments.stream()
                            .map(department -> {
                                DepartmentRequestDto dto = departmentService.convertToDepartmentRequestDto(department);
                                return dto;
                            }).toList());
        }

    }
}
