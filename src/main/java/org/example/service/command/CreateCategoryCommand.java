package org.example.service.command;

import lombok.Getter;
import org.example.service.Command;

@Getter
public class CreateCategoryCommand extends Command {

  private final String name;

  public CreateCategoryCommand(String name) {
    this.name = name;
  }
}
