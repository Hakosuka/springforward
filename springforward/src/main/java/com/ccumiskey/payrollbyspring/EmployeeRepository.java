package com.ccumiskey.payrollbyspring;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface supports CRUD actions on the database.
 */
interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
