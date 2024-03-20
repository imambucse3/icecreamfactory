package com.example.icecreamfactory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long employeeId;
	
	@Column(name="employeeName")
	public String employeeName;
	
	@Column(name="employeeAddress")
	public String employeeAddress;
	
	@Column(name="employeePhone")
	public String employeePhone;
	
	@Column(name="employeeSalary")
	public String employeeSalary;    //employee salary daily basis implement initially.
	
	@Column(name="employementType")
	public String employementType;   //daily basis or monthly payable
	
	@Column(name="lastIncrementDate")
	public String lastIncrementDate;

	public Long getEmployeeId() {
		return employeeId;
	}

	
	
}
