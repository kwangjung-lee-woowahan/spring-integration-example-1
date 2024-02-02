package org.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long count;

  public Category(Long id, String name, Long count) {
    this.id = id;
    this.name = name;
    this.count = count;
  }

  public static Category newOne(String name) {
    return new Category(null, name, 0L);
  }

  public void increaseCount() {
    this.count += 1;
  }

  public void decreaseCount() {
    this.count -= 1;
  }
}
