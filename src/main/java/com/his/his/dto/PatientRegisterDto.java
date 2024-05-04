package com.his.his.dto;

import com.his.his.models.Patient;
import com.his.his.models.Patient.BloodGroup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientRegisterDto implements Serializable{
    String name ;
    String aabhaId ;
    // String aadharId;
    String emailId;
    String dateOfBirth;
    String emergencyContactNumber;
    Patient.Gender gender;
    Patient.PatientType patientType;
    Patient.BloodGroup bloodGroup;
}


