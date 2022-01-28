package com.igor.company.Interface;

import com.igor.company.model.dto.EmployeeRq;
import com.igor.company.model.dto.SalaryRq;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getById(Long id);
    public ResponseEntity<?> insertEmployee(EmployeeRq employee);
    public ResponseEntity<?> updateEmployee(EmployeeRq employee, Long id);
    public ResponseEntity<?> updateSalary(SalaryRq salary, Long id);
    public ResponseEntity<?> deleteEmployee(Long id);

}
