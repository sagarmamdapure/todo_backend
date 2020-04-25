package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskListDao;
import com.example.todospringboot.entity.TaskList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

  private final TaskListDao taskListDao;

  public TaskListServiceImpl(TaskListDao taskListDao) {
    this.taskListDao = taskListDao;
  }

  @Override
  @Transactional
  public List<TaskList> getAllTaskList(String userName) {
    return taskListDao.getAllTaskList(userName);
  }

  @Override
  @Transactional
  public void deleteTaskList(int taskListId, String userName) {
    taskListDao.deleteTaskList(taskListId, userName);
  }

  @Override
  @Transactional
  public TaskList getTaskList(int taskListId, String userName) {
    return taskListDao.getTaskList(taskListId, userName);
  }

  @Override
  @Transactional
  public void updateTaskList(int taskListId, TaskList taskList, String userName) {
    taskListDao.updateTaskList(taskListId, taskList, userName);
  }

  @Override
  @Transactional
  public void addTaskList(TaskList taskList) {
    taskListDao.addTaskList(taskList);
  }
}
