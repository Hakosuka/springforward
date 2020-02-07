package com.ccumiskey.payrollbyspring.order;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        //Add unconditional links to single-item entities and an aggregate root
        EntityModel<Order> orderEntityModel = new EntityModel<>(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders"));
        //Add conditional links based on order status
        if(order.getStatus() == OrderStatus.IN_PROGRESS) {
            orderEntityModel.add(
                    linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
            orderEntityModel.add(
                    linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
        }
        return orderEntityModel;
    }
}
