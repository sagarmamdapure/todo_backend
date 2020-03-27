package com.example.todospringboot.service;

import com.example.todospringboot.dao.SubTaskDao;
import com.example.todospringboot.entity.SubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class SubTaskServiceImpl implements SubTaskService {

  @Autowired
  private SubTaskDao subTaskDao;

  @Override
  @Transactional
  public List<SubTask> getAllSubTask() {
    return subTaskDao.getAllSubTask();
  }

  @Override
  @Transactional
  public SubTask getSubTask(int subTaskId) throws EntityNotFoundException {
    return subTaskDao.getSubTask(subTaskId);
  }

  @Override
  @Transactional
  public void deleteSubTask(int subTaskId) {
    subTaskDao.deleteSubTask(subTaskId);
  }

  @Transactional
  @Override
  public void addSubTask(int taskId, SubTask subTask) {
    subTaskDao.addSubTask(taskId, subTask);
  }

  @Override
  @Transactional
  public void updateSubTask(int subTaskId, SubTask subTask) {
    subTaskDao.updateSubTask(subTaskId, subTask);
  }
}
