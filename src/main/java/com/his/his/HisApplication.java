package com.his.his;

import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.models.Employee_Department;
import com.his.his.models.HeadNurse_Department;
import com.his.his.repository.DepartmentRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.his.his.models.Patient;
import com.his.his.models.Patient_Department;
import com.his.his.models.Patient_Doctor;
import com.his.his.models.Role;
import com.his.his.models.User;
import com.his.his.models.CompositePrimaryKeys.Patient_DoctorId;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;
import com.his.his.models.User.EmployeeType;
import com.his.his.repository.PatientRepository;
import com.his.his.repository.Patient_DoctorRepository;
import com.his.his.repository.UserRepository;
import com.his.his.services.Employee_DepartmentService;
import com.his.his.services.HeadNurse_DepartmentService;
import com.his.his.services.Patient_DepartmentService;

@SpringBootApplication
public class HisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

	@Autowired
	private UserRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	// @Autowired
	// private ConsultationRepository consultationRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private Patient_DoctorRepository patientDoctorRepository;

	@Autowired
	Patient_DepartmentService patientDepartmentService;

	@Autowired
	Employee_DepartmentService employeeDepartmentService;

	@Autowired
	HeadNurse_DepartmentService headNurse_DepartmentService;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User employee = new User();
		employee.setDateOfBirth("12/05/12");
		employee.setName("Karan");
		employee.setLastCheckIn("1:02");
		employee.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		employee.setRole(Role.DOCTOR);
		employee.setPassword(encoder.encode("1234"));
		employee.setEmployeeType(EmployeeType.DOCTOR);

		User employee1 = new User();
		employee1.setName("Darshak");
		employee1.setDateOfBirth("12/03/12");
		employee1.setLastCheckIn("1:02");
		employee1.setEmployeeStatus(User.EmployeeStatus.CHECKED_OUT);
		employee1.setRole(Role.NURSE);
		employee1.setPassword(encoder.encode("1234"));
		employee1.setEmployeeType(EmployeeType.NURSE);

		User employee2 = new User();
		employee2.setName("John");
		employee2.setDateOfBirth("01/01/1980");
		employee2.setLastCheckIn("2:00");
		employee2.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		employee2.setRole(Role.HEAD_NURSE);
		employee2.setPassword(encoder.encode("1234"));
		employee2.setEmployeeType(EmployeeType.HEAD_NURSE);

		employeeRepository.save(employee);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);

		Patient patient = new Patient();
		patient.setName("Karan");
		patient.setAabhaId("1234");
		patient.setAadharId("123456");
		patient.setEmailId("kadn@gmail.com");
		patient.setDateOfBirth("12/06/2002");
		patient.setEmergencyContactNumber("45454545454");
		patient.setGender(Gender.MALE);
		patient.setPatientType(PatientType.INPATIENT);
		patient.setAge(15);

		Patient patient1=new Patient();
		patient1.setName("Darshak");
		patient1.setAabhaId("1234");
		patient1.setAadharId("123456");
		patient1.setEmailId("kadn@gmail.com");
		patient1.setDateOfBirth("12/06/2002");
		patient1.setEmergencyContactNumber("45454545454");
		patient1.setGender(Gender.MALE);
		patient1.setPatientType(PatientType.OUTPATIENT);

		patientRepository.save(patient);

		patientRepository.save(patient1);

		Department department1 = new Department("Orthopaedics", "Karanjit", 5, 10);
		Department department2 = new Department("Pediatrics", "Darshak", 1, 12);
		departmentRepository.save(department1);
		departmentRepository.save(department2);

		UUID doctorId = employee.getEmployeeId();
		UUID patientId = patient.getPatientId();
		User doctor = employeeRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id " + doctorId));
		// Patient patient = patientRepository.findById(patientId)
		// 		.orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id " + patientId));

		// Check if the relationship already exists
		Patient_DoctorId id = new Patient_DoctorId(patientId, doctorId);
		if (patientDoctorRepository.existsById(id)) {
			throw new IllegalArgumentException("The relationship between the patient and the doctor already exists");
		}

		// Create a new relationship and save it
		Patient_Doctor patientDoctor = new Patient_Doctor();
		patientDoctor.setId(id);
		patientDoctor.setPatient(patient);
		patientDoctor.setDoctor(doctor);
		patientDoctorRepository.save(patientDoctor);

		Patient_Department patientDepartment = patientDepartmentService.addPatient_Department(patient.getPatientId(),
				department1.getDepartmentId());

		Employee_Department doctorDepartment = employeeDepartmentService.addEmployee_Department(doctor.getEmployeeId(),
				department1.getDepartmentId());

		Employee_Department nurseDepartment = employeeDepartmentService
				.addEmployee_Department(employee1.getEmployeeId(), department1.getDepartmentId());

		HeadNurse_Department headNurse_Department = headNurse_DepartmentService
				.addHeadNurse_Department(employee2.getEmployeeId(), department1.getDepartmentId());

		System.out.println("Patient ID = " + patient.getPatientId().toString());
		// System.out.println("Department ID = "+
		// department1.getDepartmentId().toString());
		System.out.println("Department ID =" + department1.getDepartmentId().toString());
		System.out.println("Doctor ID = " + doctor.getEmployeeId().toString() + " and the Patient Id = "
				+ patient.getPatientId().toString());

		System.out.println("Patient ID = " + patient.getPatientId().toString() + " and the Department ID = "
				+ department1.getDepartmentId().toString());

		System.out.println("Doctor ID = " + doctor.getEmployeeId().toString() + " and the Department ID = "
				+ department1.getDepartmentId().toString());

		System.out.println("Nurse ID = " + employee1.getEmployeeId().toString() + " and the Department ID = "
				+ department1.getDepartmentId().toString());

		System.out.println("HeadNurse ID = "+employee2.getEmployeeId().toString() + " and the Department ID = "+department1.getDepartmentId().toString());
		
	}

}
