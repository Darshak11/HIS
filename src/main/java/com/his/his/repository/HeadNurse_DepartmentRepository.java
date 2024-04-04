package com.his.his.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.his.his.models.Department;
import com.his.his.models.HeadNurse_Department;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.HeadNurse_DepartmentId;

public interface HeadNurse_DepartmentRepository extends JpaRepository<HeadNurse_Department,HeadNurse_DepartmentId>{

    HeadNurse_Department findHeadNursesByDepartment(Department department);

    HeadNurse_Department findDepartmentByHeadNurse(User headNurse);
    
    
}
