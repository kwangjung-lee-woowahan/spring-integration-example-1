package org.example.config;

import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.util.Set;
import org.example.listener.Event;
import org.example.service.Command;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * 채널정의: 각 채널을 정의해 준다. 단, 여기서 channel 을 정의하지 않아도 ServiceActivator 에서 DirectChannel 을 만들어 주긴 함. (여기서 만든 것이 우선)
 */
@Configuration
public class ServiceConfig {

  private final GenericWebApplicationContext context;
  private final ThreadPoolTaskExecutor messageTaskExecutor;
  private final ThreadPoolTaskExecutor eventTaskExecutor;

  public ServiceConfig(GenericWebApplicationContext context,
      ThreadPoolTaskExecutor messageTaskExecutor, ThreadPoolTaskExecutor eventTaskExecutor) {
    this.context = context;
    this.messageTaskExecutor = messageTaskExecutor;
    this.eventTaskExecutor = eventTaskExecutor;
  }

  @Bean
  public IntegrationFlow commandRoute() {
    return IntegrationFlow.from("ServiceGateway")
        .route((Command command) -> command.getClass().getSimpleName())
        .get();
  }

//  @Bean(name = "CreateCategoryCommand")
//  public MessageChannel createCategoryCommand() {
//    return new DirectChannel();
//  }
//
//  @Bean(name = "CreatePostCommand")
//  public MessageChannel createPostCommand() {
//    return new DirectChannel();
//  }
//
//  @Bean(name = "DeletePostCommand")
//  public MessageChannel deletePostCommand() {
//    return new DirectChannel();
//  }

  @PostConstruct
  public void registerCommandChannels() {
    // 경로 찾기
    URL url = ClasspathHelper.forClass(Command.class);
    // 스캐너 등록
    ConfigurationBuilder builder = new ConfigurationBuilder()
        .setUrls(url)
        .setScanners(Scanners.SubTypes);
    // Reflections 객체 생성
    Reflections reflections = new Reflections(builder);

    Set<Class<? extends Command>> subTypes = reflections.getSubTypesOf(Command.class);
    subTypes.forEach(aClass ->
        context.registerBean(aClass.getSimpleName(), ExecutorChannel.class,
            () -> new ExecutorChannel(messageTaskExecutor)));
  }

  @Bean
  public IntegrationFlow eventRoute() {
    return IntegrationFlow.from("Handler")
        .route((Event event) -> event.getClass().getSimpleName())
        .get();
  }

  @Bean(name = "PostCreatedEvent")
  public MessageChannel postCreatedEvent() {
    return new PublishSubscribeChannel(eventTaskExecutor);
  }

  @Bean(name = "PostDeletedEvent")
  public MessageChannel postDeletedEvent() {
    return new PublishSubscribeChannel(eventTaskExecutor);
  }

  @PostConstruct
  public void registerEventChannels() {
    // 경로 찾기
    URL url = ClasspathHelper.forClass(Event.class);
    // 스캐너 등록
    ConfigurationBuilder builder = new ConfigurationBuilder()
        .setUrls(url)
        .setScanners(Scanners.SubTypes);
    // Reflections 객체 생성
    Reflections reflections = new Reflections(builder);

    Set<Class<? extends Event>> subTypes = reflections.getSubTypesOf(Event.class);

  }
}
