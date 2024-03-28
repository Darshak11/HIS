package com.his.his.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.models.Patient;
import com.his.his.models.Patient_Department;
import com.his.his.models.Patient_Doctor;
import com.his.his.models.CompositePrimaryKeys.Patient_DepartmentId;
import com.his.his.repository.DepartmentRepository;
import com.his.his.repository.PatientRepository;
import com.his.his.repository.Patient_DepartmentRepository;

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
    public Patient_DepartmentService(Patient_DepartmentRepository patientDepartmentRepository, PatientRepository patientRepository, DepartmentRepository departmentRepository) {
        this.patientDepartmentRepository = patientDepartmentRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    public Patient_Department addPatient_Department(UUID patientId, UUID departmentId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id " + patientId));
        
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("Department not exist with id " + departmentId));
        
        Patient_DepartmentId id = new Patient_DepartmentId(patientId,departmentId);

        if (patientDepartmentRepository.existsById(id)) {
            throw new IllegalArgumentException("The relationship between the patient and the department already exists");
        }
        
        Patient_Department patientDepartment = new Patient_Department();
        patientDepartment.setId(id);
        patientDepartment.setPatient(patient);
        patientDepartment.setDepartment(department);
        return patientDepartmentRepository.save(patientDepartment);
    }

    public ResponseEntity<List<Patient>> getAllPatientsByDepartmentID(UUID departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFoundException("Department not exist with id " + departmentId));
        List<Patient_Department> patientDepartment = patientDepartmentRepository.findPatientsByDepartment(department);

        List<Patient> patients = patientDepartment.stream().map(Patient_Department::getPatient).toList();
        
        if(patients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(patients);
        }
    }

    public ResponseEntity<List<DepartmentRequestDto>> getAllDepartmentsByPatientID(UUID patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient not exist with id " + patientId));

        List<Patient_Department> patientDepartment = patientDepartmentRepository.findDepartmentsByPatient(patient);

        List<DepartmentRequestDto> departments = patientDepartment.stream().map(Patient_Department::getDepartment).map(department -> {
            DepartmentRequestDto dto = new DepartmentRequestDto();
            dto.setDepartmentId(department.getDepartmentId());
            dto.setDepartmentName(department.getDepartmentName());
            dto.setDepartmentHead(department.getDepartmentHead());
            dto.setNoOfDoctors(department.getNoOfDoctors());
            dto.setNoOfNurses(department.getNoOfNurses());
            return dto;
        }).toList();
        
        if(departments.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(departments);
        }
    }
}
