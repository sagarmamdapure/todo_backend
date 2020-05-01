package com.example.todospringboot.service;

import com.example.todospringboot.dao.SubTaskDao;
import com.example.todospringboot.entity.SubTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SubTaskServiceImpl implements SubTaskService {

  private final SubTaskDao subTaskDao;

  public SubTaskServiceImpl(SubTaskDao subTaskDao) {
    this.subTaskDao = subTaskDao;
  }

  @Override
  @Transactional
  public List<SubTask> getAllSubTask(String userName, int taskId) {
    return subTaskDao.getAllSubTask(userName, taskId);
  }

  @Override
  @Transactional
  public SubTask getSubTask(int subTaskId, String userName) throws EntityNotFoundException {
    return subTaskDao.getSubTask(subTaskId, userName);
  }

  @Override
  @Transactional
  public void deleteSubTask(int subTaskId, String userName) {
    subTaskDao.deleteSubTask(subTaskId, userName);
  }

  @Transactional
  @Override
  public void addSubTask(int taskId, SubTask subTask) {
    subTaskDao.addSubTask(taskId, subTask);
  }

  @Override
  @Transactional
  public void updateSubTask(int subTaskId, SubTask subTask, String userName) {
    subTaskDao.updateSubTask(subTaskId, subTask, userName);
  }
}
