package com.his.his.models.CompositePrimaryKeys;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class DoctorId {
    private UUID doctorId;
    private UUID departmentId;
}
