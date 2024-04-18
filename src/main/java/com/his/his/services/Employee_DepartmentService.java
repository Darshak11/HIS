package com.his.his.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.his.his.dto.DepartmentRequestDto;
import com.his.his.dto.EmployeeRequestDto;
import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.models.Employee_Department;
import com.his.his.models.Role;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.Employee_DepartmentId;
import com.his.his.repository.DepartmentRepository;
import com.his.his.repository.Employee_DepartmentRepository;
import com.his.his.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Employee_DepartmentService {

	@Autowired
	private final Employee_DepartmentRepository employeeDepartmentRepository;

	@Autowired
	private final DepartmentRepository departmentRepository;

	@Autowired
	private final UserRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
    private PublicPrivateService publicPrivateService;

	@Autowired
	public Employee_DepartmentService(Employee_DepartmentRepository employeeDepartmentRepository,
			DepartmentRepository departmentRepository, UserRepository employeeRepository) {
		this.employeeDepartmentRepository = employeeDepartmentRepository;
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
	}

	public Employee_Department addEmployee_Department(UUID employeeId, UUID departmentId) {

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not exist with id " + departmentId));

		User employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Employee not exist with id " + employeeId));

		// Check if the employee is already associated with a department
		List<Employee_Department> existingAssociations = employeeDepartmentRepository
				.findDepartmentsByEmployee(employee);
		if (!existingAssociations.isEmpty()) {
			throw new IllegalArgumentException("The employee is already associated with a department");
		}

		Employee_DepartmentId id = new Employee_DepartmentId(employeeId, departmentId);

		if (employeeDepartmentRepository.existsById(id)) {
			throw new IllegalArgumentException(
					"The relationship between the employee and the department already exists");
		}

		Employee_Department employeeDepartment = new Employee_Department();
		employeeDepartment.setId(id);
		employeeDepartment.setDepartment(department);
		employeeDepartment.setEmployee(employee);
		return employeeDepartmentRepository.save(employeeDepartment);
	}

	public List<EmployeeRequestDto> getAllEmployeesByDepartmentID(UUID departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not exist with id " + departmentId));
		List<Employee_Department> employeeDepartment = employeeDepartmentRepository
				.findEmployeesByDepartment(department);

		List<EmployeeRequestDto> employees = employeeDepartment.stream().map(Employee_Department::getEmployee)
				.map(employee -> {
					EmployeeRequestDto dto = employeeService.convertEmployeeToDto(employee);
					return dto;
				}).toList();

		return employees;
	}

	public ResponseEntity<DepartmentRequestDto> getDepartmentByEmployeeID(UUID employeeId) {
		User employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Employee not exist with id " + employeeId));
		List<Employee_Department> employeeDepartment = employeeDepartmentRepository
				.findDepartmentsByEmployee(employee);

		if (employeeDepartment.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			Department department = employeeDepartment.get(0).getDepartment();
			DepartmentRequestDto dto = departmentService.convertToDepartmentRequestDto(department);
			return ResponseEntity.ok(dto);
		}
	}

	public List<EmployeeRequestDto> getAllNursesByDepartmentID(UUID departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not exist with id " + departmentId));
		List<Employee_Department> employeeDepartment = employeeDepartmentRepository
				.findEmployeesByDepartment(department);

		List<EmployeeRequestDto> nurses = employeeDepartment.stream().map(Employee_Department::getEmployee)
				.filter(employee -> Role.NURSE.equals(employee.getRole())
						|| Role.HEAD_NURSE.equals(employee.getRole()))
				.map(employee -> {
					EmployeeRequestDto dto = employeeService.convertEmployeeToDto(employee);
					return dto;
				}).toList();

		return nurses;
	}

	public List<EmployeeRequestDto> getAllDoctorsByDepartmentID(UUID departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department not exist with id " + departmentId));
		List<Employee_Department> employeeDepartment = employeeDepartmentRepository
				.findEmployeesByDepartment(department);

		List<EmployeeRequestDto> doctors = employeeDepartment.stream().map(Employee_Department::getEmployee)
				.filter(employee -> Role.DOCTOR.equals(employee.getRole()))
				.map(employee -> {
					EmployeeRequestDto dto = employeeService.convertEmployeeToDto(employee);
					return dto;
				}).toList();

		return doctors;
	}

}