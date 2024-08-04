package com.talee.employee.management.controller;

import com.talee.employee.management.model.Employee;
import com.talee.employee.management.service.EmployeeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
    return service.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Employee createEmployee(@RequestBody Employee employee) {
    return service.save(employee);
  }


  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id,
      @RequestBody Employee employeeDetails) {
    return service.updateEmployee(id, employeeDetails)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
    return service.findById(id)
        .map(employee -> {
          service.deleteById(id);
          return ResponseEntity.ok().build();
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
