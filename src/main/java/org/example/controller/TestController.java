package org.example.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  private final ApplicationContext context;

  public TestController(ApplicationContext context) {
    this.context = context;
  }

  @GetMapping("/test")
  public String test() {
    MessageChannel messageChannel = (MessageChannel) context.getBean("CreatePostCommand");

    return "OK";
  }
}
