package org.example.listener;

import org.example.listener.event.PostCreatedEvent;
import org.example.listener.event.PostDeletedEvent;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class ExternalListener {

  @ServiceActivator(inputChannel = "PostCreatedEvent")
  public void handle(PostCreatedEvent event) {
    System.out.println("[ExternalHandler] PostCreatedEvent. PostCreatedEvent.categoryId=" + event.getCategoryId());
  }

  @ServiceActivator(inputChannel = "PostDeletedEvent")
  public void handle(PostDeletedEvent event) {
    System.out.println("[ExternalHandler] PostDeletedEvent. PostDeletedEvent.categoryId=" + event.getCategoryId());
  }
}
