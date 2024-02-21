package com.his.his;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.his.his.models.Employee;
import com.his.his.models.Patient;
import com.his.his.models.Patient.Gender;
import com.his.his.models.Patient.PatientType;
import com.his.his.repository.EmployeeRepository;
import com.his.his.repository.PatientRepository;

import java.util.UUID;

@SpringBootApplication
public class HisApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(HisApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public void run(String ...args) throws Exception{
		Employee employee=new Employee();
		employee.setDateOfBirth("12/05/12");
		employee.setName("Karan");
		employee.setLastCheckIn("1:02");
		employee.setEmployeeStatus(Employee.EmployeeStatus.CHECKED_IN);
		Employee employee1=new Employee();
		employee1.setName("Darshak");
		employee1.setDateOfBirth("12/03/12");
		employee1.setLastCheckIn("1:02");
		employee1.setEmployeeStatus(Employee.EmployeeStatus.CHECKED_OUT);
		employeeRepository.save(employee);
		employeeRepository.save(employee1);


		Patient patient=new Patient();
		patient.setAabhaId("1234");
		patient.setDateOfBirth("12/06/2002");
		patient.setGender(Gender.MALE);
		patient.setName("Karan");
		patient.setPatientType(PatientType.INPATIENT);

		patientRepository.save(patient);


	}

}
