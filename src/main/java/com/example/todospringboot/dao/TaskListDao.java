package com.example.todospringboot.dao;

import com.example.todospringboot.entity.TaskList;

import java.util.List;

public interface TaskListDao {

  List<TaskList> getAllTaskList(String userName);

  void deleteTaskList(int taskListId, String userName);

  TaskList getTaskList(int taskListId);

  void updateTaskList(int taskListId, TaskList taskList, String userName);

  void addTaskList(TaskList taskList);
}
