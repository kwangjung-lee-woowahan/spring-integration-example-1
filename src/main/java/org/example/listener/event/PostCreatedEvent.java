package org.example.listener.event;

import lombok.Getter;
import org.example.listener.Event;

@Getter
public class PostCreatedEvent extends Event {

  private final Long categoryId;

  public PostCreatedEvent(Long categoryId) {
    this.categoryId = categoryId;
  }
}
