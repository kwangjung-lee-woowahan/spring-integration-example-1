package org.example.controller;

import org.example.controller.requests.CreateCategoryRequest;
import org.example.service.ServiceGateway;
import org.example.service.command.CreateCategoryCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  private final ServiceGateway gateway;

  public CategoryController(ServiceGateway gateway) {
    this.gateway = gateway;
  }

  @PostMapping("/create-category")
  public Long createCategory(@RequestBody CreateCategoryRequest request) {
    return gateway.request(new CreateCategoryCommand(request.getName()));
  }
}
