package org.example.service;

import org.example.domain.Post;
import org.example.listener.MessagePublisher;
import org.example.listener.event.PostCreatedEvent;
import org.example.listener.event.PostDeletedEvent;
import org.example.repository.PostRepository;
import org.example.service.command.CreatePostCommand;
import org.example.service.command.DeletePostCommand;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final MessagePublisher messagePublisher;

  public PostService(PostRepository postRepository, MessagePublisher messagePublisher) {
    this.postRepository = postRepository;
    this.messagePublisher = messagePublisher;
  }

  @Transactional
  @ServiceActivator(inputChannel = "CreatePostCommand")
  public Long handle(CreatePostCommand command) {
    System.out.println("[PostService] CreatePostCommand");
    Post post = Post.newOne(command.getCategoryId(), command.getContent());

    messagePublisher.publish(new PostCreatedEvent(post.getCategoryId()));
    Post saved = postRepository.save(post);

    return saved.getId();
  }

  @ServiceActivator(inputChannel = "DeletePostCommand")
  public void handle(DeletePostCommand command) {
    System.out.println("[PostService] DeletePostCommand");
    Post post = postRepository.getReferenceById(command.getId());

    post.delete();
    messagePublisher.publish(new PostDeletedEvent(post.getCategoryId()));
    postRepository.delete(post);
  }
}
