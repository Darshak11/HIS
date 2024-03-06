package com.his.his.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class DepartmentRequestDto {
    String departmentName;
    String departmentHead;
    int noOfDoctors;
    int noOfNurses;
}
