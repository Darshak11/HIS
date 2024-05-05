package com.his.his;

import com.his.his.exception.ResourceNotFoundException;
import com.his.his.models.Department;
import com.his.his.models.Employee_Department;
import com.his.his.models.HeadNurse_Department;
import com.his.his.repository.DepartmentRepository;

import java.util.UUID;

import com.his.his.services.DepartmentService;
import com.his.his.services.EmailService;
import com.his.his.services.EmployeeService;

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
import com.his.his.models.Patient.BloodGroup;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;
import com.his.his.models.User.EmployeeType;
import com.his.his.repository.PatientRepository;
import com.his.his.repository.Patient_DoctorRepository;
import com.his.his.repository.UserRepository;
import com.his.his.services.Employee_DepartmentService;
import com.his.his.services.HeadNurse_DepartmentService;
import com.his.his.services.PatientService;
import com.his.his.services.Patient_DepartmentService;
import com.his.his.services.PublicPrivateService;

@SpringBootApplication
public class HisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

	@Autowired
	private UserRepository employeeRepository;

	// @Autowired
	// private PatientRepository patientRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PatientService patientService;

	@Autowired 
	private DepartmentService departmentService;

	@Autowired
	private Patient_DoctorRepository patientDoctorRepository;

	@Autowired
	Patient_DepartmentService patientDepartmentService;

	@Autowired
	Employee_DepartmentService employeeDepartmentService;

	@Autowired
	HeadNurse_DepartmentService headNurse_DepartmentService;

	@Autowired
	PublicPrivateService publicPrivateIdService;

	@Autowired
	EmailService emailService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("RUNNER ");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User employee = new User();
		employee.setDateOfBirth("12/05/12");
		employee.setName("Karan");
		employee.setLastCheckIn("1:02");
		employee.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		employee.setRole(Role.DOCTOR);
		employee.setEmail("efef");
		employee.setPassword(encoder.encode("1234"));
		employee.setEmployeeType(EmployeeType.DOCTOR);
		User doctor2 = new User();
		doctor2.setDateOfBirth("12/05/12");
		doctor2.setName("Karan");
		doctor2.setLastCheckIn("1:02");
		doctor2.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		doctor2.setRole(Role.DOCTOR);
		doctor2.setEmail("efef");
		doctor2.setPassword(encoder.encode("1234"));
		doctor2.setEmployeeType(EmployeeType.DOCTOR);

		User employee1 = new User();
		employee1.setName("Darshak");
		employee1.setDateOfBirth("12/03/12");
		employee1.setLastCheckIn("1:02");
		employee1.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		employee1.setRole(Role.NURSE);
		employee1.setEmail("nivnervk");
		employee1.setPassword(encoder.encode("1234"));
		employee1.setEmployeeType(EmployeeType.NURSE);

		User employee2 = new User();
		employee2.setName("John");
		employee2.setDateOfBirth("01/01/1980");
		employee2.setLastCheckIn("2:00");
		employee2.setEmployeeStatus(User.EmployeeStatus.CHECKED_IN);
		employee2.setRole(Role.HEAD_NURSE);
		employee2.setEmail("evgeve");
		employee2.setPassword(encoder.encode("1234"));
		employee2.setEmployeeType(EmployeeType.HEAD_NURSE);

		employeeService.createEmployee(employee);
		employeeService.createEmployee(employee1);
		employeeService.createEmployee(employee2);
		employeeService.createEmployee(doctor2);

		Patient patient = new Patient();
		patient.setName("Karan");
		patient.setAabhaId("1234");
		// patient.setAadharId("123456");
		patient.setEmailId("kadn@gmail.com");
		patient.setDateOfBirth("12/06/2002");
		patient.setEmergencyContactNumber("45454545454");
		patient.setGender(Gender.MALE);
		patient.setPatientType(PatientType.INPATIENT);
		patient.setBloodGroup(BloodGroup.B_POSITIVE);

		Patient patient1=new Patient();
		patient1.setName("Darshak");
		patient1.setAabhaId("1234");
		// patient1.setAadharId("123456");
		patient1.setEmailId("kadn@gmail.com");
		patient1.setDateOfBirth("12/06/2002");
		patient1.setEmergencyContactNumber("45454545454");
		patient1.setGender(Gender.MALE);
		patient1.setPatientType(PatientType.OUTPATIENT);
		patient1.setBloodGroup(BloodGroup.AB_POSITIVE);

		patientService.createPatient(patient);
		patientService.createPatient(patient1);

		Department department1 = new Department("Orthopaedics", "Karanjit", 5, 10);
		Department department2 = new Department("Pediatrics", "Darshak", 1, 12);
		departmentService.createDepartment(department1);
		departmentService.createDepartment(department2);


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
		employeeDepartmentService.addEmployee_Department(doctor2.getEmployeeId(),department1.getDepartmentId());
		Employee_Department nurseDepartment = employeeDepartmentService
				.addEmployee_Department(employee1.getEmployeeId(), department1.getDepartmentId());

		HeadNurse_Department headNurse_Department = headNurse_DepartmentService
				.addHeadNurse_Department(employee2.getEmployeeId(), department1.getDepartmentId());

		System.out.println("Patient ID = " + publicPrivateIdService.publicIdByPrivateId(patient.getPatientId()).toString());
		// System.out.println("Department ID = "+
		// department1.getDepartmentId().toString());
		System.out.println("Department ID =" + publicPrivateIdService.publicIdByPrivateId (department1.getDepartmentId()).toString());
		System.out.println("Doctor ID = " + publicPrivateIdService.publicIdByPrivateId(doctor.getEmployeeId()).toString() + " and the Patient Id = " + publicPrivateIdService.publicIdByPrivateId(patient.getPatientId()).toString());

		System.out.println("Patient ID = " + publicPrivateIdService.publicIdByPrivateId(patient.getPatientId()).toString() + " and the Department ID = "
				+ publicPrivateIdService.publicIdByPrivateId(department1.getDepartmentId()).toString());

		System.out.println("Doctor ID = " + publicPrivateIdService.publicIdByPrivateId(doctor.getEmployeeId()).toString() + " and the Department ID = "
				+ publicPrivateIdService.publicIdByPrivateId(department1.getDepartmentId()).toString());

		System.out.println("Nurse ID = " + publicPrivateIdService.publicIdByPrivateId(employee1.getEmployeeId()).toString() + " and the Department ID = "
				+ publicPrivateIdService.publicIdByPrivateId(department1.getDepartmentId()).toString());

		System.out.println("HeadNurse ID = "+publicPrivateIdService.publicIdByPrivateId(employee2.getEmployeeId()).toString() + " and the Department ID = "+publicPrivateIdService.publicIdByPrivateId(department1.getDepartmentId()).toString());


		// System.out.println("Testing email service");
//		emailService.sendEmail("karanjitsaha12@gmail.com", "Test Email", "This is a test email from Spring Boot");
		// emailService.sendHtmlEmail("karanjitsaha12@gmail.com","Test Email");


	}

}
