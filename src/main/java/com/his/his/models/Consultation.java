package com.his.his.models;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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

	@MapsId("patientId")
    @OneToOne
    @JoinColumn(name = "patientId", referencedColumnName = "patientId", insertable = false, updatable = false)
    private Patient patient;

    @MapsId("employeeId")
    @OneToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", insertable = false, updatable = false)
    private User doctor;

    
    @Column(name="EMRID",updatable = false,nullable = false)
    private UUID emrId;
}
