package org.example.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(
    defaultRequestChannel = "ServiceGateway"
)
public interface ServiceGateway {

  <T> T request(Command command);
}
