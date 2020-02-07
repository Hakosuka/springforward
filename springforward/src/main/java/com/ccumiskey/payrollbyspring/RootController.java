package com.ccumiskey.payrollbyspring;

import com.ccumiskey.payrollbyspring.employee.EmployeeController;
import com.ccumiskey.payrollbyspring.order.OrderController;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping
    RepresentationModel index() {
        RepresentationModel rootRepModel = new RepresentationModel();
        rootRepModel.add(linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
        rootRepModel.add(linkTo(methodOn(OrderController.class).all()).withRel("orders"));
        return rootRepModel;
    }
}
