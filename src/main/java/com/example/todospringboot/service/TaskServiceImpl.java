package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskDao;
import com.example.todospringboot.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskServiceImpl implements TaskService {

  @Autowired
  private TaskDao taskDao;

  @Override
  @Transactional
  public List<Task> getAllTask(String userName) {
    return taskDao.getAllTask(userName);
  }

  @Override
  @Transactional
  public void deleteTask(int taskId) {
    taskDao.deleteTask(taskId);
  }

  @Override
  @Transactional
  public Task getTask(int taskId) {
    return taskDao.getTask(taskId);
  }

  @Override
  @Transactional
  public void updateTask(int taskId, Task task) {
    taskDao.updateTask(taskId, task);
  }

  @Override
  @Transactional
  public void addTask(int taskListId, Task task) {
    taskDao.addTask(taskListId, task);
  }

  @Override
  @Transactional
  public List<Task> getAllTaskFromTaskList(String userName, int taskListId) {
    return taskDao.getAllTaskFromTaskList(userName, taskListId);
  }
}
