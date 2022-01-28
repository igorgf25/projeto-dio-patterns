package com.igor.company.controller;

import com.igor.company.model.dto.EmployeeRq;
import com.igor.company.model.dto.SalaryRq;
import com.igor.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class employeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return employeeService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<?> insertEmployee(@RequestBody EmployeeRq employee){
        return employeeService.insertEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRq employee, @PathVariable("id") Long id){
        return employeeService.updateEmployee(employee, id);
    }

    @PutMapping("/salary/{id}")
    public ResponseEntity<?> updateSalary(@RequestBody SalaryRq newSalary, @PathVariable("id") Long id){
        return employeeService.updateSalary(newSalary, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        return employeeService.deleteEmployee(id);
    }
}
