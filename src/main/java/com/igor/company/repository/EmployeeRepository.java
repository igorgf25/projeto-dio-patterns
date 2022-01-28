package com.igor.company.repository;

import com.igor.company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    int countById(Long id);

    int countByRG(String rg);
}
