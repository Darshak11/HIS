package com.his.his.dto;

import com.his.his.models.Employee;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRegisterDto implements Serializable {
    String name ;
    String dob ;
    String lastCheckIn;
    Employee.EmployeeStatus employeeStatus;
}
