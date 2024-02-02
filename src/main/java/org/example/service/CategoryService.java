package org.example.service;

import org.example.domain.Category;
import org.example.repository.CategoryRepository;
import org.example.service.command.CreateCategoryCommand;
import org.example.service.command.DecreaseCategoryCountCommand;
import org.example.service.command.IncreaseCategoryCountCommand;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

//  public Long create(String name) {
//    Category category = Category.newOne(name);
//
//    Category saved = categoryRepository.save(category);
//
//    return saved.getId();
//  }

  @ServiceActivator(inputChannel = "CreateCategoryCommand")
  public Long handle(CreateCategoryCommand command) {
    System.out.println("[CategoryService] CreateCategoryCommand");
    Category category = Category.newOne(command.getName());

    Category saved = categoryRepository.save(category);

    return saved.getId();
  }

  @Transactional  // 없으면 could not initialize proxy - no session 에러 발생
  @ServiceActivator(inputChannel = "IncreaseCategoryCountCommand")
  public void handle(IncreaseCategoryCountCommand command) {
    System.out.println("[CategoryService] IncreaseCategoryCountCommand");
    Category category = categoryRepository.getReferenceById(command.getCategoryId());

    category.increaseCount();
    categoryRepository.save(category);
  }

  @ServiceActivator(inputChannel = "DecreaseCategoryCountCommand")
  public void handle(DecreaseCategoryCountCommand command) {
    System.out.println("[CategoryService] DecreaseCategoryCountCommand");
    Category category = categoryRepository.getReferenceById(command.getCategoryId());

    category.decreaseCount();
    categoryRepository.save(category);
  }
}
