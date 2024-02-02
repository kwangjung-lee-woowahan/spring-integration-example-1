package org.example.listener;

import org.example.listener.event.PostCreatedEvent;
import org.example.listener.event.PostDeletedEvent;
import org.example.service.command.DecreaseCategoryCountCommand;
import org.example.service.command.IncreaseCategoryCountCommand;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class CategoryListener {

  private final MessagePublisher messagePublisher;

  public CategoryListener(MessagePublisher messagePublisher) {
    this.messagePublisher = messagePublisher;
  }

  @ServiceActivator(inputChannel = "PostCreatedEvent")
  public void handle(PostCreatedEvent event) {
    System.out.println("[CategoryHandler] PostCreatedEvent");
    messagePublisher.publish(new IncreaseCategoryCountCommand(event.getCategoryId()));
  }

  @ServiceActivator(inputChannel = "PostDeletedEvent")
  public void handle(PostDeletedEvent event) {
    System.out.println("[CategoryHandler] PostDeletedEvent");
    messagePublisher.publish(new DecreaseCategoryCountCommand(event.getCategoryId()));
  }
}
