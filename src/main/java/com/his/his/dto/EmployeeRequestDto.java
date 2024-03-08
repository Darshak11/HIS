package com.his.his.dto;
import java.io.Serializable;
import java.util.UUID;

import com.his.his.models.User.EmployeeStatus;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestDto implements Serializable
{
    UUID employeeId;
    String name;
    String dateOfBirth;
    String lastCheckIn;
    EmployeeStatus employeeStatus;
}
