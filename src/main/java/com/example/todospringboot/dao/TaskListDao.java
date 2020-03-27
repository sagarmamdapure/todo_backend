package com.example.todospringboot.dao;


import com.example.todospringboot.entity.TaskList;

import java.util.List;

public interface TaskListDao {

  List<TaskList> getAllTaskList();

  void deleteTaskList(int taskListId);

  TaskList getTaskList(int taskListId);

  void updateTaskList(int taskListId, TaskList taskList);

  void addTaskList(TaskList taskList);
}
