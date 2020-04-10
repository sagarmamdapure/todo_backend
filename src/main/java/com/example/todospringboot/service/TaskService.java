package com.example.todospringboot.service;

import com.example.todospringboot.entity.Task;

import java.util.List;

public interface TaskService {

  List<Task> getAllTask(String userName);

  void deleteTask(int taskId, String userName);

  Task getTask(int taskId);

  void updateTask(int taskId, Task task, String userName);

  void addTask(int taskListId, Task task);

  List<Task> getAllTaskFromTaskList(String userName, int taskListId);
}
