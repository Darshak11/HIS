package com.his.his.repository;

import com.his.his.models.CompositePrimaryKeys.Patient_DoctorId;
import com.his.his.models.Patient;
import com.his.his.models.Patient_Doctor;
import com.his.his.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface Patient_DoctorRepository extends JpaRepository<Patient_Doctor, Patient_DoctorId> {

    List<Patient_Doctor> findPatientsByDoctor(User doctor);

    List<Patient_Doctor> findDoctorsByPatient(Patient patient);

    

}
