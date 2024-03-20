package com.example.icecreamfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icecreamfactory.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
