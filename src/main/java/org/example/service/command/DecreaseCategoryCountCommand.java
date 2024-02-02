package org.example.service.command;

import lombok.Getter;
import org.example.service.Command;

@Getter
public class DecreaseCategoryCountCommand extends Command {

  private final Long categoryId;

  public DecreaseCategoryCountCommand(Long categoryId) {
    this.categoryId = categoryId;
  }
}
