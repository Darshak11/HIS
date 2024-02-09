package com.his.his.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "employee")
public class Employee
{
    public enum EmployeeStatus {
        CHECKED_IN, CHECKED_OUT
    }


    @Id
    @GeneratedValue
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false )
    private UUID employeeId;

    @Column(name = "Name")
    private String name;

    @Column(name = "DateOfBirth")
    private String dateOfBirth;

    @Column(name = "LastCheckIn")
    private String lastCheckIn;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

}
