package com.his.his.models;

import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.his.his.converter.ObjectCryptoConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "department")
public class Department {
    public Department() {

    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "departmentId", updatable = false, nullable = false)
    private UUID departmentId;

    @Convert(converter = ObjectCryptoConverter.class)
    @Column(nullable = false)
    private String departmentName;

    @Convert(converter = ObjectCryptoConverter.class)
    @Column(nullable = false)
    private String departmentHead;

    @Column(nullable = false)
    private int noOfDoctors;

    @Column(nullable = false)
    private int noOfNurses;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Employee_Department> employeeDepartments;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Patient_Department> patientDepartments;

    public Department(String departmentName, String departmentHead, int noOfDoctors, int noOfNurses) {
        this.departmentName = departmentName;
        this.departmentHead = departmentHead;
        this.noOfDoctors = noOfDoctors;
        this.noOfNurses = noOfNurses;
    }
}
