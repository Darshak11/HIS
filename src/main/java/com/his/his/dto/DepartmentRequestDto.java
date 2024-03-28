package com.his.his.dto;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class DepartmentRequestDto {
    UUID departmentId;
    String departmentName;
    String departmentHead;
    int noOfDoctors;
    int noOfNurses;
}
