package com.example.todospringboot.service;


import com.example.todospringboot.entity.SubTask;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface SubTaskService {

  List<SubTask> getAllSubTask(String userName, int taskId);

  SubTask getSubTask(int subTaskId) throws EntityNotFoundException;

  void deleteSubTask(int subTaskId);

  void addSubTask(int taskId, SubTask subTask);

  void updateSubTask(int subTaskId, SubTask subTask);
}
