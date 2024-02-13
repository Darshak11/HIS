package com.his.his.models;

import jakarta.persistence.*;
import java.util.UUID;
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
    @GeneratedValue
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private UUID patientId;
    
    @Column(name = "Name", nullable = false )
    private String name;

    @Column(name = "aabhaId", nullable = false )
    private String aabhaId;

    @Column(name = "DateOfBirth", nullable = false )
    private String dateOfBirth;

    @Column(name = "Gender", nullable = false )
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "patientType", nullable = false )
    @Enumerated(EnumType.STRING)
    private PatientType patientType;

    @Column(name = "DischargeStatus", nullable = false )
    @Enumerated(EnumType.STRING)
    private DischargeStatus dischargeStatus;

}
