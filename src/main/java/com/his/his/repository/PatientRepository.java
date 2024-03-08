package com.his.his.repository;

import com.his.his.models.Patient;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,UUID> {

    List<Patient> findByName(String patientName);
}
