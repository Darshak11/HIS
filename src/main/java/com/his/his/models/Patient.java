package com.his.his.models;

import jakarta.persistence.*;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

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

    public Patient(){

    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="patientId",updatable = false, nullable = false )
    private UUID patientId;
    
    @Column(name = "Name", nullable = false )
    private String name;

    @Column(name = "aabhaId", nullable = false )
    private String aabhaId;

    @Column(name = "aadharId", nullable = false )
    private String aadharId;

    @Column(name = "emailId", nullable = false )
    private String emailId;

    @Column(name = "DateOfBirth", nullable = false )
    private String dateOfBirth;

    @Column(name="Emergency Contact Number",nullable = false)
    private String emergencyContactNumber;

    @Column(name = "Gender", nullable = false )
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "patientType", nullable = false )
    @Enumerated(EnumType.STRING)
    private PatientType patientType;

    @Column(name = "DischargeStatus")
    @Enumerated(EnumType.STRING)
    private DischargeStatus dischargeStatus;

    @Column(name="age",nullable =false)
    private int age;
    

}
