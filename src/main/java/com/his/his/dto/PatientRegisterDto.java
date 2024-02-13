package com.his.his.dto;

import com.his.his.models.Patient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientRegisterDto implements Serializable{
    String name ;
    String aabhaId ;
    String dob ;
    Patient.Gender gender;
    Patient.PatientType patientType;
    Patient.DischargeStatus dischargeStatus;

}


