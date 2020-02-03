package com.ccumiskey.payrollbyspring;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class EmployeeController {
    private final EmployeeRepository repo;

    EmployeeController(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/employees")
    List<Employee> all() {
        return repo.findAll();
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repo.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
        return repo.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repo.save(employee);
                }).orElseGet(() -> {
                    newEmployee.setId(id);
                    return repo.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
