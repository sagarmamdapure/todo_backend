package com.example.todospringboot.config;

import com.example.todospringboot.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.todospringboot")
public class AppConfig {

  @Bean
  public SubTaskService subTaskService() {
    return new SubTaskServiceImpl();
  }

  @Bean
  public TaskService taskService() {
    return new TaskServiceImpl();
  }

  @Bean
  public TaskListService taskListService() {
    return new TaskListServiceImpl();
  }

  @Bean
  public UserRepositoryService userRepositoryService() {
    return new UserRepositoryServiceImpl();
  }

  @Bean
  public RoleRepositoryService roleRepositoryService() {
    return new RoleRepositoryServiceImpl();
  }

}
