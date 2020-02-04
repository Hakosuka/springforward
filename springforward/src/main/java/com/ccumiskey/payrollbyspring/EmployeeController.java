package com.ccumiskey.payrollbyspring;

import com.ccumiskey.payrollbyspring.errorhandling.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
class EmployeeController {
    private final EmployeeRepository employeeRepo;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeRepository employeeRepo, EmployeeModelAssembler assembler) {
        this.employeeRepo = employeeRepo;
        this.assembler = assembler;
    }

    /**
     * Return all of the employees on the database
     * @return All of the employees on the database
     */
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = employeeRepo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(employees,
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    /**
     * Create a new employee in the database
     * @param newEmployee: the new employee to be added to the database
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) throws URISyntaxException {
        EntityModel<Employee> entityModel = assembler.toModel(employeeRepo.save(newEmployee));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /**
     * Given an employee's ID, return their details.
     * @param id: the ID of the employee to be returned
     * @return the employee corresponding to the input ID
     */
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
         Employee employee = employeeRepo.findById(id)
                 .orElseThrow(() -> new EmployeeNotFoundException(id));
         return assembler.toModel(employee);
    }

    /**
     * Given an employee's ID, update their details.
     * @param updatedEmployee: The updated employee data to be inserted
     * @param id: The ID of the updated employee
     * @return
     */
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee updatedEmployee, @PathVariable Long id){
        return employeeRepo.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    return employeeRepo.save(employee);
                }).orElseGet(() -> {
                    updatedEmployee.setId(id);
                    return employeeRepo.save(updatedEmployee);
                });
    }

    /**
     * Given an employee's ID, delete it from the database
     * @param id: The employee to be deleted
     */
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        try {
            employeeRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.info("Employee ID" + String.valueOf(id) + " not found");
        }
    }
}
