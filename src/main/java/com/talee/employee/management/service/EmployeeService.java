package com.talee.employee.management.service;

import com.talee.employee.management.model.Employee;
import com.talee.employee.management.repo.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final EmployeeRepository repository;

  public EmployeeService(EmployeeRepository repository) {
    this.repository = repository;
  }

  public List<Employee> findAll() {
    return repository.findAll();
  }

  public Optional<Employee> findById(Integer id) {
    return repository.findById(id);
  }

  public Employee save(Employee employee) {
    return repository.save(employee);
  }

  public void deleteById(Integer id) {
    repository.deleteById(id);
  }

  public Optional<Employee> updateEmployee(Integer id, Employee employeeDetails) {
    return repository.findById(id).map(employee -> {
      employee.setName(employeeDetails.getName());
      employee.setEmail(employeeDetails.getEmail());
      employee.setNationality(employeeDetails.getNationality());
      employee.setOld(employeeDetails.getOld());
      return Optional.of(repository.save(employee));
    }).orElse(Optional.empty());
  }
}
