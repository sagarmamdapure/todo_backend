package com.example.todospringboot.dao;

import com.example.todospringboot.entity.SubTask;
import com.example.todospringboot.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class SubTaskDaoImpl implements SubTaskDao {

  final TaskDao taskDao;
  final SessionFactory sessionFactory;
  private Logger logger = Logger.getLogger(getClass().getName());

  public SubTaskDaoImpl(SessionFactory sessionFactory, TaskDao taskDao) {
    this.sessionFactory = sessionFactory;
    this.taskDao = taskDao;
  }

  @Override
  public List<SubTask> getAllSubTask() {
    Session session = this.sessionFactory.getCurrentSession();
    Query<SubTask> theQuery = session.createQuery("from SubTask", SubTask.class);
    return theQuery.getResultList();
  }

  @Override
  public void deleteSubTask(int subTaskId) {

    SubTask subTask = this.getSubTask(subTaskId);
    Session session = this.sessionFactory.getCurrentSession();
    session.delete(subTask);
  }

  @Override
  public void addSubTask(int taskId, SubTask subTask) {
    Session session = this.sessionFactory.getCurrentSession();
    Task task = this.taskDao.getTask(taskId);
    subTask.setTask(task);
    session.save(subTask);
  }

  @Override
  public SubTask getSubTask(int subTaskId) throws EntityNotFoundException {
    Session session = this.sessionFactory.getCurrentSession();
    Query<SubTask> query = session.createQuery("from SubTask where id=:subTaskId", SubTask.class);
    query.setParameter("subTaskId", subTaskId);
    return query.getSingleResult();
  }

  @Override
  public void updateSubTask(int subTaskId, SubTask subTask) {
    Session session = this.sessionFactory.getCurrentSession();
    SubTask subTaskOrig = this.getSubTask(subTaskId);
    if (subTask.getTask() != null) {
      subTaskOrig.setTask(subTask.getTask());
    }
    if (subTask.getSubTaskDescription() != null) {
      subTaskOrig.setSubTaskDescription(subTask.getSubTaskDescription());
    }
    if (subTask.getSubTaskStatus() != null) {
      subTaskOrig.setSubTaskStatus(subTask.getSubTaskStatus());
    }
    if (subTask.getUserName() != null) {
      subTaskOrig.setUserName(subTask.getUserName());
    }
    session.update(subTaskOrig);
  }
}
