package com.his.his.models.CompositePrimaryKeys;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NurseId implements Serializable {
    private UUID nurseId;
    private UUID headNurseId;
    private UUID departmentId;
}
