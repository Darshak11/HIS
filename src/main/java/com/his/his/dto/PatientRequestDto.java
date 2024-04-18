package com.his.his.dto;

import java.io.Serializable;
import java.util.UUID;

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
    private String patientId;
    private String name;
    private String aabhaId;
    private String aadharId;
    private String emailId;
    private String dateOfBirth;
    private String emergencyContactNumber;
    private Gender gender;
    private PatientType patientType;
    private DischargeStatus dischargeStatus;
    private int age;
}
