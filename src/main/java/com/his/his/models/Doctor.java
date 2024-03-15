package com.his.his.models;

import java.util.UUID;

import com.his.his.models.CompositePrimaryKeys.DoctorId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "doctor")
public class Doctor{
    @EmbeddedId
    private DoctorId id;

    @JoinColumn(name="users", referencedColumnName = "employeeId",nullable = false, insertable = false, updatable = false)
    @MapsId("employeeId")
    private User doctor;

    @ManyToOne
    @JoinColumn(name="department", referencedColumnName = "departmentId",nullable = false, insertable = false, updatable = false)
    @MapsId("departmentId")
    private Department department;

    @Column(name = "departmentId",nullable=false)
    private UUID departmentId;

}
