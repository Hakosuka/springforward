package com.ccumiskey.payrollbyspring.order;

import com.ccumiskey.payrollbyspring.errorhandling.OrderNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    private final OrderRepository orderRepo;
    private final OrderModelAssembler assembler;

    OrderController(OrderRepository repo, OrderModelAssembler assembler) {
        this.orderRepo = repo;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orders = orderRepo.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        return assembler.toModel(orderRepo.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        Order newOrder = orderRepo.save(order);
        return ResponseEntity.created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }

    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<RepresentationModel> cancel(@PathVariable Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if(order.getStatus() == OrderStatus.IN_PROGRESS){
            order.setStatus(OrderStatus.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(orderRepo.save(order)));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "Order ID" + order.getId().toString() + " can't be cancelled because its status is " + order.getStatus()));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<RepresentationModel> complete(@PathVariable long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if(order.getStatus() == OrderStatus.IN_PROGRESS){
            order.setStatus(OrderStatus.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(orderRepo.save(order)));
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed",
                        "Order ID" + order.getId().toString() + " can't be completed because its status is " + order.getStatus()));
    }
}