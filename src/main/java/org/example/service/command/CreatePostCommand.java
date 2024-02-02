package org.example.service.command;

import lombok.Getter;
import org.example.service.Command;

@Getter
public class CreatePostCommand extends Command {

  private final Long categoryId;
  private final String content;

  public CreatePostCommand(Long categoryId, String content) {
    this.categoryId = categoryId;
    this.content = content;
  }
}
