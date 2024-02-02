package org.example.listener;

import java.util.Map;
import org.example.service.Command;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

  private final ApplicationContext context;

  public MessagePublisher(ApplicationContext context) {
    this.context = context;
  }

  public void publish(Command command) {
    MessageChannel messageChannel = (MessageChannel) context.getBean("ServiceGateway");
    messageChannel.send(
        new GenericMessage<>(command, Map.of(MessageHeaders.REPLY_CHANNEL, "nullChannel"))
    );
  }

  public void publish(Event event) {
    String channelName = event.getClass().getSimpleName();
    MessageChannel messageChannel = (MessageChannel) context.getBean(channelName);
    messageChannel.send(new GenericMessage<>(event));
  }
}
