package com.his.his.services;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository=departmentRepository;
    }

    public List<DepartmentRequestDto> getAllDepartments(){
        return departmentRepository.findAll().stream()
                .map(department -> {
                    DepartmentRequestDto dto = convertToDepartmentRequestDto(department);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private DepartmentRequestDto convertToDepartmentRequestDto(Department department) {
        DepartmentRequestDto dto = new DepartmentRequestDto();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentHead(department.getDepartmentHead());
        dto.setDepartmentName(department.getDepartmentName());
        dto.setNoOfDoctors(department.getNoOfDoctors());
        dto.setNoOfNurses(department.getNoOfNurses());
        return dto;
    }

    public DepartmentRequestDto createDepartment(Department department){
        Department savedDepartment = departmentRepository.save(department);
        DepartmentRequestDto dto = convertToDepartmentRequestDto(savedDepartment);
        dto.setDepartmentId(department.getDepartmentId());
        return dto;
    }

    public DepartmentRequestDto findById(UUID id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));
        return convertToDepartmentRequestDto(department);
    }

    public DepartmentRequestDto updateDepartment(UUID id, Department departmentDetails){
        Department updatedepartment = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));

        updatedepartment.setDepartmentHead(departmentDetails.getDepartmentHead());
        updatedepartment.setDepartmentName(departmentDetails.getDepartmentName());
        updatedepartment.setNoOfNurses(departmentDetails.getNoOfNurses());
        updatedepartment.setNoOfDoctors(departmentDetails.getNoOfDoctors());
        Department updatedDepartment= departmentRepository.save(updatedepartment);
        return convertToDepartmentRequestDto(updatedDepartment);
    }

    public ResponseEntity<HttpStatus> deleteDepartment(UUID id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));
        departmentRepository.delete(department);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public List<UUID> getDepartmentIdByName(String departmentName) {
        List<UUID> depIds = new ArrayList<>();
        List<Department> departments = departmentRepository.findByDepartmentName(departmentName);
        if(!departments.isEmpty()){
            for(Department department:departments){
                depIds.add(department.getDepartmentId());
            }
            return depIds;
        }
        else{
            throw new ResourceNotFoundException("Departments with name "+departmentName+" not found");
        }
    }

}
