package com.his.his.services;

import com.his.his.dto.EmployeeRegisterDto;
import com.his.his.dto.EmployeeRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.User;
import com.his.his.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@Transactional
// @RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    private final UserRepository employeeRepository;

    @Autowired
    public EmployeeService(UserRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<?> signup(EmployeeRegisterDto registerDto) {
        // Create patient object from DTO
        User employee = new User();
        employee.setName(registerDto.getName());
        employee.setDateOfBirth(registerDto.getDob());
        // employee.setEmployeeStatus(registerDto.getEmployeeStatus());
        // employee.setLastCheckIn(registerDto.getLastCheckIn());

        // Put this in Exception block for handling failure
        employeeRepository.save(employee);

        return new ResponseEntity<>("Patient Registered Successfully", HttpStatus.OK);
    }

    public List<EmployeeRequestDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(user -> {
                    EmployeeRequestDto dto = new EmployeeRequestDto();
                    dto.setEmployeeId(user.getEmployeeId());
                    dto.setName(user.getName());
                    dto.setDateOfBirth(user.getDateOfBirth());
                    dto.setLastCheckIn(user.getLastCheckIn());
                    dto.setEmployeeStatus(user.getEmployeeStatus());
                    // set other fields
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EmployeeRequestDto getEmployeeById(UUID id) {
        User user = employeeRepository.findById(id);
                

        EmployeeRequestDto userDto = new EmployeeRequestDto();
        userDto.setEmployeeId(user.getEmployeeId());
        userDto.setName(user.getName());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setLastCheckIn(user.getLastCheckIn());
        userDto.setEmployeeStatus(user.getEmployeeStatus());
        // set other fields

        return userDto;
    }

    public EmployeeRequestDto updateEmployee(UUID id, User employeeDetails) {
        EmployeeRequestDto updateEmployee = getEmployeeById(id);
        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setDateOfBirth(employeeDetails.getDateOfBirth());
        updateEmployee.setEmployeeStatus(employeeDetails.getEmployeeStatus());
        return updateEmployee;
    }

    public ResponseEntity<HttpStatus> deleteEmployee(UUID id) {
        User employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exists " + id));
        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public List<UUID> getEmployeeIdByName(String employeeName){
        List<UUID> emplIds=new ArrayList<>();
        List<User> employees=employeeRepository.findByName(employeeName);

        if(!employees.isEmpty()){
            for(User user:employees){
                emplIds.add(user.getEmployeeId());
            }
            return emplIds;
        }
        else{
            throw new ResourceNotFoundException("Employees with name "+employeeName+" not found");
        }
    }
}
