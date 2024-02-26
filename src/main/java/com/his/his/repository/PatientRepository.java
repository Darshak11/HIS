package com.his.his.repository;

import com.his.his.models.Patient;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,UUID> {

    Optional<Patient> findByName(String patientName);
}
