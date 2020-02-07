package com.ccumiskey.payrollbyspring;

import com.ccumiskey.payrollbyspring.employee.Employee;
import com.ccumiskey.payrollbyspring.employee.EmployeeRepository;
import com.ccumiskey.payrollbyspring.order.Order;
import com.ccumiskey.payrollbyspring.order.OrderRepository;
import com.ccumiskey.payrollbyspring.order.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepo, OrderRepository orderRepo) {
        return args -> {
            employeeRepo.save(new Employee("Mark", "Corrigan", "Senior credit manager"));
            employeeRepo.save(new Employee("Jeremy", "Usbourne", "Jez"));
            employeeRepo.findAll().forEach(employee -> {
                log.info("Preloaded " + employee);
            });
            orderRepo.save(new Order("Twix", OrderStatus.IN_PROGRESS));
            orderRepo.save(new Order("Ribena", OrderStatus.IN_PROGRESS));
            orderRepo.save(new Order("Twirl", OrderStatus.IN_PROGRESS));
            orderRepo.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}
