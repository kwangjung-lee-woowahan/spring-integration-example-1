package org.example.listener.event;

import lombok.Getter;
import org.example.listener.Event;

@Getter
public class PostDeletedEvent extends Event {

  private final Long categoryId;

  public PostDeletedEvent(Long categoryId) {
    this.categoryId = categoryId;
  }
}
