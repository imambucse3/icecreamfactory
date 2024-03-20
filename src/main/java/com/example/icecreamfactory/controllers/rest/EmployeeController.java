package com.example.icecreamfactory.controllers.rest;

import java.util.Date;
import java.util.List;

import com.example.icecreamfactory.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.icecreamfactory.entity.Employee;
import com.example.icecreamfactory.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;


	@GetMapping("/date")
	@ResponseBody
	public String getTime()
	{
		Date date=new Date();
		
		return String.valueOf(date);
	}

	@PostMapping("/employees")
	public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
		System.out.println("Save Employee...");
		employeeService.saveEmployee(employee);
		return ResponseEntity.status(HttpStatus.CREATED).body("Employee Ssaved");
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployee() {
		List<Employee> listOfEmployees=employeeService.getAllEmployees();
		return ResponseEntity.status(HttpStatus.OK).body(listOfEmployees);
		
	}

	@DeleteMapping("/employees/{employeeId}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") Long id) {
		// Logic to delete the employee with the given ID
		employeeService.delteEmployee(id);
		return ResponseEntity.ok("Employee deleted successfully");
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) {
		Employee _employee = employeeService.updateEmployee(employee, id);
		return new ResponseEntity<>(_employee, HttpStatus.OK);
	}
}
