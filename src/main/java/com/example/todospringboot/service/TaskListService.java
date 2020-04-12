package com.example.todospringboot.service;

import com.example.todospringboot.entity.TaskList;

import java.util.List;

public interface TaskListService {

  List<TaskList> getAllTaskList(String userName);

  void deleteTaskList(int taskListId, String userName);

  TaskList getTaskList(int taskListId, String userName);

  void updateTaskList(int taskListId, TaskList taskList, String userName);

  void addTaskList(TaskList taskList);
}
