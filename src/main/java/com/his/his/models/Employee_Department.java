package com.his.his.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.his.his.models.CompositePrimaryKeys.Employee_DepartmentId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employee_department")
public class Employee_Department {
    @EmbeddedId
    private Employee_DepartmentId id;

    // @MapsId("employeeId")
    // @OneToOne
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JoinColumn(name = "employeeId", referencedColumnName = "employeeId",
    // insertable = false, updatable = false)
    @OneToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", insertable = false, updatable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId", insertable = false, updatable = false)
    private Department department;
}
