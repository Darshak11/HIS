package com.his.his.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.models.HeadNurse_Department;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.HeadNurse_DepartmentId;
import com.his.his.repository.DepartmentRepository;
import com.his.his.repository.HeadNurse_DepartmentRepository;
import com.his.his.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HeadNurse_DepartmentService {

    @Autowired
    private final HeadNurse_DepartmentRepository headNurse_DepartmentRepository;

    @Autowired
    private final UserRepository headNurseRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    public HeadNurse_DepartmentService(HeadNurse_DepartmentRepository headNurse_DepartmentRepository,
            UserRepository headNurseRepository, DepartmentRepository departmentRepository) {
        this.headNurse_DepartmentRepository = headNurse_DepartmentRepository;
        this.headNurseRepository = headNurseRepository;
        this.departmentRepository = departmentRepository;
    }

    public HeadNurse_Department addHeadNurse_Department(UUID headNurseId, UUID departmentId) {
        User headNurse = headNurseRepository.findById(headNurseId)
                .orElseThrow(() -> new ResourceNotFoundException("HeadNurse not exist with id " + headNurseId));
        ;
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not exist with id " + departmentId));

        HeadNurse_DepartmentId id = new HeadNurse_DepartmentId(headNurseId, departmentId);

        if (headNurse_DepartmentRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "The relationship between the head nurse and the department already exists");
        }

        HeadNurse_Department headNurseDepartment = new HeadNurse_Department();
        headNurseDepartment.setId(id);
        headNurseDepartment.setHeadNurse(headNurse);
        headNurseDepartment.setDepartment(department);
        return headNurse_DepartmentRepository.save(headNurseDepartment);
    }

    public User getHeadNurseByDepartmentID(UUID departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not exist with id " + departmentId));

        HeadNurse_Department headNurse_Department = headNurse_DepartmentRepository
                .findHeadNursesByDepartment(department);

        if (headNurse_Department == null) {
            throw new ResourceNotFoundException("HeadNurse not exist with department id " + departmentId);
        } else {
            return headNurse_Department.getHeadNurse();
        }
    }

    public Department getDepartmentByHeadNurseID(UUID headNurseId) {

        User headNurse = headNurseRepository.findById(headNurseId)
                .orElseThrow(() -> new ResourceNotFoundException("HeadNurse not exist with id " + headNurseId));

        HeadNurse_Department headNurse_Department = headNurse_DepartmentRepository.findDepartmentByHeadNurse(headNurse);
        Department department = headNurse_Department.getDepartment();

        if (department == null) {
            throw new ResourceNotFoundException("No such department exists with HeadNurse as "+headNurseId.toString());
        } else {
            return department;
        }
    }

}
