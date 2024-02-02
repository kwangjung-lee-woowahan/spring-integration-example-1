package org.example.service.command;

import lombok.Getter;
import org.example.service.Command;

@Getter
public class DeletePostCommand extends Command {

  private final Long id;

  public DeletePostCommand(Long id) {
    this.id = id;
  }
}
