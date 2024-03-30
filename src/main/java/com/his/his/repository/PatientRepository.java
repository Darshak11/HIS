package com.his.his.repository;

import com.his.his.models.Patient;
import com.his.his.models.Patient.PatientType;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,UUID> {

    List<Patient> findByName(String patientName);

    List<Patient> findByPatientType(PatientType patientType);
}
