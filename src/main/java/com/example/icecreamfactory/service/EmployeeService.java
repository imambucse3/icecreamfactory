package com.example.icecreamfactory.service;

import java.util.List;

import com.example.icecreamfactory.entity.Employee;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee saveEmployee(Employee employee);

	void delteEmployee(Long id);

    Employee updateEmployee(Employee employee, Long id);
}
