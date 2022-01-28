package com.igor.company.service;

import com.igor.company.Interface.IEmployeeService;
import com.igor.company.exceptions.ApiRequestException;
import com.igor.company.model.Employee;
import com.igor.company.model.dto.EmployeeRq;
import com.igor.company.model.dto.SalaryRq;
import com.igor.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> getAll(){
        try{
            return new ResponseEntity<>(employeeRepository.findAll(), HttpStatus.OK);
        } catch(Exception err) {

            return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getById(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Id does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> insertEmployee(EmployeeRq employee){
        int countRg = getCountRg(employee.getRG());
        if(countRg == 0){
            Employee employeeSave = new Employee();
            changeToEmployee(employee, employeeSave);

            try{
                var response = employeeRepository.save(employeeSave);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }catch (Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }

        }else {
            throw new ApiRequestException("Rg already registered");
        }
    }

    @Override
    public ResponseEntity<?> updateEmployee(EmployeeRq employee, Long id){
        int countId = getCountId(id);
        int countRg = getCountRg(employee.getRG());

        if(countRg == 1 && countId == 1){
            boolean sameRg = sameRg(employee.getRG(), id);
            if(!sameRg){
                throw new ApiRequestException("Rg already exist");
            }
        }

        if(countId == 1) {
            Employee employeeSave = new Employee();
            changeToEmployee(employee, employeeSave);
            employeeSave.setId(id);

            try{
                var response = employeeRepository.save(employeeSave);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new ApiRequestException("Id does not exist");
        }
    }

    @Override
    public ResponseEntity<?> updateSalary(SalaryRq salary, Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            Employee employeeSave = employee.get();
            employeeSave.setSalary(salary.getSalary());
            try{
                var response = employeeRepository.save(employeeSave);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }catch(Exception err) {
                return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
            }
        }else {
            throw new ApiRequestException("Id does not exist");
        }
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Long id){
        int countId = getCountId(id);

        if(countId == 1) {
            try {
                employeeRepository.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } catch(Exception err){
                return new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);
            }

        }else {
            throw new ApiRequestException("Id does not exist");
        }
    }

    private void changeToEmployee(EmployeeRq employee, Employee employeeSave) {
        employeeSave.setRG(employee.getRG());
        employeeSave.setName(employee.getName());
        employeeSave.setAddress(employee.getAddress());
        employeeSave.setEmail(employee.getEmail());
        employeeSave.setSalary(employee.getSalary());
    }

    private int getCountRg(String RG) {
        return employeeRepository.countByRG(RG);
    }

    private int getCountId(Long id) {
        return employeeRepository.countById(id);
    }

    private boolean sameRg(String Rg, Long id) {
        return Rg.equals(employeeRepository.getById(id).getRG());
    }

}
