package com.his.his.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.his.models.Department;
import com.his.his.models.Patient;
import com.his.his.models.Patient_Department;
import com.his.his.models.CompositePrimaryKeys.Patient_DepartmentId; 

public interface Patient_DepartmentRepository extends JpaRepository<Patient_Department, Patient_DepartmentId>{

    List<Patient_Department> findPatientsByDepartment(Department department);

    List<Patient_Department> findDepartmentsByPatient(Patient patient);


    
}
