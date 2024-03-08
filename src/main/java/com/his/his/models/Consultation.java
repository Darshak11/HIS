package com.his.his.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name="consultation")
public class Consultation {
    public Consultation() {

	}

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false )
    private UUID consultationId;

	@Column(name="patientID",updatable = false,nullable = false)
    private UUID patientId;

    @Column(name="doctorID",updatable = false,nullable = false)
    private UUID doctorId;

    @Column(name="EMRID",updatable = false,nullable = false)
    private UUID emrId;
}
