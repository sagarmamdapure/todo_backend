package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskDao;
import com.example.todospringboot.entity.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  public TaskServiceImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  @Transactional
  public List<Task> getAllTask(String userName) {
    return taskDao.getAllTask(userName);
  }

  @Override
  @Transactional
  public void deleteTask(int taskId, String userName) {
    taskDao.deleteTask(taskId, userName);
  }

  @Override
  @Transactional
  public Task getTask(int taskId, String userName) {
    return taskDao.getTask(taskId, userName);
  }

  @Override
  @Transactional
  public void updateTask(int taskId, Task task, String userName) {
    taskDao.updateTask(taskId, task, userName);
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
