package com.sop.chapter9.productservice.rest;


import com.sop.chapter9.productservice.command.CreateProductCommand;
import io.axoniq.axonserver.grpc.command.Command;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    //ข้อ3
   /* @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel model){
        return "product created: " + model.getTitle();
    } */

    private final CommandGateway commandGateway;

    @Autowired
    public ProductController(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel model){

        CreateProductCommand command = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(model.getTitle())
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .build();

        String result;
        try{
            result = commandGateway.sendAndWait(command);
        } catch (Exception e){
            result = e.getLocalizedMessage();
        }
        return result;
    }

    @DeleteMapping
    public String deleteProduct(){
        return "product deleted";
    }

}
