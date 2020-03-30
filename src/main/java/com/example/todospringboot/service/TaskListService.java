package com.example.todospringboot.service;

import com.example.todospringboot.entity.TaskList;

import java.util.List;

public interface TaskListService {

  List<TaskList> getAllTaskList(String userName);

  void deleteTaskList(int taskListId);

  TaskList getTaskList(int taskListId);

  void updateTaskList(int taskListId, TaskList taskList);

  void addTaskList(TaskList taskList);
}
