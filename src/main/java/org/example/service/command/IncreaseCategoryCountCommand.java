package org.example.service.command;

import lombok.Getter;
import org.example.service.Command;

@Getter
public class IncreaseCategoryCountCommand extends Command {

  private final Long categoryId;

  public IncreaseCategoryCountCommand(Long categoryId) {
    this.categoryId = categoryId;
  }
}
