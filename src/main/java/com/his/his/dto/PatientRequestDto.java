package com.his.his.dto;

import java.io.Serializable;
import java.util.UUID;

import com.his.his.models.Patient.BloodGroup;
import com.his.his.models.Patient.DischargeStatus;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto implements Serializable {
    String patientId;
    String name;
    String aabhaId;
    String emailId;
    String dateOfBirth;
    String emergencyContactNumber;
    Gender gender;
    PatientType patientType;
    DischargeStatus dischargeStatus;
    BloodGroup bloodGroup;
}
