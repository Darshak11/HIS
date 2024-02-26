package com.his.his.contoller;

import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentRepository.save(department);
    }


    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentId(@PathVariable UUID id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));
        return ResponseEntity.ok(department);
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable UUID id, @RequestBody Department departmentDetails){
        Department updatedepartment = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));
        updatedepartment.setDepartmentHead(departmentDetails.getDepartmentHead());
        updatedepartment.setDepartmentName(departmentDetails.getDepartmentName());
        updatedepartment.setNoOfNurses(departmentDetails.getNoOfNurses());
        updatedepartment.setNoOfDoctors(departmentDetails.getNoOfDoctors());
        departmentRepository.save(updatedepartment);
        return ResponseEntity.ok(updatedepartment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable UUID id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department does not exist"));
        departmentRepository.delete(department);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getDepartmentId")
    public ResponseEntity<UUID> getDepartmentIdByName(@RequestParam String departmentName) {
        Optional<Department> departmentOptional = departmentRepository.findByDepartmentName(departmentName);
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            return ResponseEntity.ok(department.getDepartmentId());
        } else {
            throw new ResourceNotFoundException("Department with name " + departmentName + " not found");
        }
    }

    /*
        TODO
         1. Add doctor and nurse to the department.
         2. Need to create several models like doctor-department model, nurse-department etc.
         3. Need to implement list all doctors, nurses, patients in the department etc.
         4.  Create a function to get department id given a department name. Can reuse this for patient doctor etc. Since id yaad nhi rhegi bt naam yaad rhega. Department and patient ke liye krdiya baaki ka krna baaki hai

    */


}
