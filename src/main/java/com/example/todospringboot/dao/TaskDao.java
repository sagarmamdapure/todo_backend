package com.example.todospringboot.dao;


import com.example.todospringboot.entity.Task;

import java.util.List;

public interface TaskDao {

  List<Task> getAllTask();

  void deleteTask(int taskId);

  Task getTask(int taskId);

  void updateTask(int taskId, Task task);

  void addTask(int taskListId, Task task);

  List<Task> getAllTaskFromTaskList(int taskListId);
}
