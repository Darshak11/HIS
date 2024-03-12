package com.his.his.models.CompositePrimaryKeys;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Employee_DepartmentId implements Serializable{
    private UUID employeeId;
    private UUID departmentId;
}
