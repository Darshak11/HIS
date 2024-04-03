// package com.his.his.models;

// import com.his.his.models.CompositePrimaryKeys.NurseId;

// import jakarta.persistence.EmbeddedId;
// import jakarta.persistence.Entity;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.MapsId;
// import jakarta.persistence.OneToOne;
// import jakarta.persistence.Table;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;

// @Entity
// @Getter
// @Setter
// @ToString
// @Table(name = "nurse")
// public class Nurse {
//     @EmbeddedId
//     private NurseId nurseId;
    
//     @MapsId("employeeId")
//     @OneToOne
//     @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", insertable = false, updatable = false)
//     private User nurse;

//     @MapsId
//     @OneToOne
//     @JoinColumn(name = "departmentId", referencedColumnName = "departmentId", insertable = false)
//     private Department department;

//     @MapsId
//     @OneToOne
//     @JoinColumn(name = "employeeId", referencedColumnName = "employeeId", insertable = false)
//     private User headNurse;
// }
