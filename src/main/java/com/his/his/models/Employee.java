package com.his.his.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "employee")
public class Employee
{
    public Employee() {

    }

    public enum EmployeeStatus {
        CHECKED_IN, CHECKED_OUT
    }


//    @Id
//    @GeneratedValue
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column( updatable = false, nullable = false )
    // private UUID employeeId;
    private UUID employeeId;


    @Column(name = "Name", nullable = false )
    private String name;

    @Column(name = "DateOfBirth", nullable = false )
    private String dateOfBirth;

    @Column(name = "LastCheckIn" )
    private String lastCheckIn;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

}
