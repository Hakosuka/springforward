package com.ccumiskey.payrollbyspring.employee;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface supports CRUD actions on the database.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
