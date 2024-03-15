package com.his.his.models.CompositePrimaryKeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Patient_DoctorId implements Serializable {
    private UUID patientId;
    private UUID employeeId;

    // Constructors, getters, and setters
    // Make sure to implement equals and hashCode methods
}
