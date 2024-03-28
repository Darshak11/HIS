package com.his.his.models;

import com.his.his.models.CompositePrimaryKeys.Patient_DoctorId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="patient_doctor")
public class Patient_Doctor {
   @EmbeddedId
   private Patient_DoctorId id;

   @MapsId("patientId")
   @ManyToOne
   @JoinColumn(name = "patientId", referencedColumnName = "patientId", insertable = false, updatable = false)
   private Patient patient;

   @MapsId("employeeId")
   @ManyToOne
   @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", insertable = false)
   private User doctor;

}
