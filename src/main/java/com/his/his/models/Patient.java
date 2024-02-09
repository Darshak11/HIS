package com.his.his.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "patient")
public class Patient 
{
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    
    public enum PatientType {
        INPATIENT, OUTPATIENT
    }
    
    public enum DischargeStatus {
        Discharged_by_doctor, Discharged_by_nurse
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientId;
    
    @Column(name = "Name")
    private String name;

    @Column(name = "aabhaId")
    private String aabhaId;

    @Column(name = "DateOfBirth")
    private String dateOfBirth;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "patientType")
    @Enumerated(EnumType.STRING)
    private PatientType patientType;

    @Column(name = "DischargeStatus")
    @Enumerated(EnumType.STRING)
    private DischargeStatus dischargeStatus;

}
