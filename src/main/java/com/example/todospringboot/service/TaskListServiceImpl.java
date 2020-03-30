package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskListDao;
import com.example.todospringboot.entity.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TaskListServiceImpl implements TaskListService {

  @Autowired
  private TaskListDao taskListDao;

  @Override
  @Transactional
  public List<TaskList> getAllTaskList(String userName) {
    return taskListDao.getAllTaskList(userName);
  }

  @Override
  @Transactional
  public void deleteTaskList(int taskListId) {
    taskListDao.deleteTaskList(taskListId);
  }

  @Override
  @Transactional
  public TaskList getTaskList(int taskListId) {
    return taskListDao.getTaskList(taskListId);
  }

  @Override
  @Transactional
  public void updateTaskList(int taskListId, TaskList taskList) {
    taskListDao.updateTaskList(taskListId, taskList);
  }

  @Override
  @Transactional
  public void addTaskList(TaskList taskList) {
    taskListDao.addTaskList(taskList);
  }
}
