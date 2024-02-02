package org.example.controller;

import org.example.controller.requests.CreatePostRequest;
import org.example.controller.requests.DeletePostRequest;
import org.example.service.ServiceGateway;
import org.example.service.command.CreatePostCommand;
import org.example.service.command.DeletePostCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final ServiceGateway gateway;

  public PostController(ServiceGateway gateway) {
    this.gateway = gateway;
  }

  @PostMapping("/create-post")
  public Long createPost(@RequestBody CreatePostRequest request) {
    return gateway.request(new CreatePostCommand(request.getCategoryId(), request.getContent()));
  }

  @PostMapping("/delete-post")
  public void deletePost(@RequestBody DeletePostRequest request) {
    gateway.request(new DeletePostCommand(request.getPostId()));
  }
}
