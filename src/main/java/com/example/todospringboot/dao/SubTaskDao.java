package com.example.todospringboot.dao;


import com.example.todospringboot.entity.SubTask;

import java.util.List;

public interface SubTaskDao {
  List<SubTask> getAllSubTask(String userName, int taskId);

  void deleteSubTask(int subTaskId, String userName);

  void addSubTask(int taskId, SubTask subTask);

  SubTask getSubTask(int subTaskId);

  void updateSubTask(int subTaskId, SubTask subTask, String userName);
}
