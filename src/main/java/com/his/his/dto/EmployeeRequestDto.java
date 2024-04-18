package com.his.his.dto;
import java.io.Serializable;
import java.util.UUID;

import com.his.his.models.User.EmployeeStatus;
import com.his.his.models.User.EmployeeType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto implements Serializable
{
    String employeeId;
    String name;
    String dateOfBirth;
    String lastCheckIn;
    EmployeeStatus employeeStatus;
    EmployeeType employeeType;
}
