package org.example.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @AttributeOverride(name = "id", column = @Column(name = "categoryId"))
  private Long categoryId;
  private String content;

  public Post(Long id, Long categoryId, String content) {
    this.id = id;
    this.categoryId = categoryId;
    this.content = content;
  }

  public static Post newOne(Long categoryId, String content) {
    return new Post(null, categoryId, content);
  }

  public void delete() {
    // do nothing
  }
}
