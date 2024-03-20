package com.example.icecreamfactory.service.impl;

import java.util.List;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.exception.ResponseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.icecreamfactory.entity.Employee;
import com.example.icecreamfactory.repository.EmployeeRepository;
import com.example.icecreamfactory.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return this.employeeRepository.findAll();
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}

	@Override
	public void delteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee, Long id) {
		Employee _employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResponseNotFoundException("Not found Employee with id = " + id));
		_employee.setEmployeeName(employee.getEmployeeName());
		_employee.setEmployeeAddress(employee.getEmployeeAddress());
		_employee.setEmployeePhone(employee.getEmployeePhone());
		_employee.setEmployeeSalary(employee.getEmployeeSalary());
		_employee.setEmployementType(employee.getEmployementType());
		_employee.setLastIncrementDate(employee.getLastIncrementDate());
		return employeeRepository.save(_employee);
	}

}
