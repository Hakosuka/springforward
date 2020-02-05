package com.ccumiskey.payrollbyspring;

import com.ccumiskey.payrollbyspring.employee.Employee;
import com.ccumiskey.payrollbyspring.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repo) {
        return args -> {
            log.info("Preloading: " + repo.save(new Employee("Mark", "Corrigan", "Credit Manager")));
            log.info("Preloading: " + repo.save(new Employee("Jeremy", "Usbourne", "Handyman")));
        };
    }
}
