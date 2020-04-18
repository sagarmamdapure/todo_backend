package com.example.todospringboot.dao;

import com.example.todospringboot.entity.SubTask;
import com.example.todospringboot.entity.Task;
import com.example.todospringboot.exceptions.UnauthorizedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
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

  @SuppressWarnings("JpaQlInspection")
  @Override
  public List<SubTask> getAllSubTask(String userName, int taskId) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<SubTask> query =
            session.createQuery(
                    "from SubTask where task_id=:taskId and userName=:userName", SubTask.class);
    query.setParameter("taskId", taskId);
    query.setParameter("userName", userName);
    return query.getResultList();
  }

  @Override
  public void deleteSubTask(int subTaskId, String userName) {

    SubTask subTask = this.getSubTask(subTaskId, userName);
    if (subTask.getUserName().equals(userName)) {
      Session session = this.sessionFactory.getCurrentSession();
      session.delete(subTask);
    } else
      throw new UnauthorizedException(
              String.format("This subtask doesn't belong to %s user", userName));
  }

  @Override
  public void addSubTask(int taskId, SubTask subTask) {
    Session session = this.sessionFactory.getCurrentSession();
    Task task = this.taskDao.getTask(taskId, subTask.getUserName());
    task.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
    subTask.setTask(task);
    session.save(subTask);
  }

  @Override
  public SubTask getSubTask(int subTaskId, String userName) throws EntityNotFoundException {
    Session session = this.sessionFactory.getCurrentSession();
    Query<SubTask> query =
            session.createQuery(
                    "from SubTask where id=:subTaskId and userName=:userName", SubTask.class);
    query.setParameter("subTaskId", subTaskId);
    query.setParameter("userName", userName);
    return query.getSingleResult();
  }

  @Override
  public void updateSubTask(int subTaskId, SubTask subTask, String userName) {
    Session session = this.sessionFactory.getCurrentSession();
    SubTask subTaskOrig = this.getSubTask(subTaskId, userName);
    if (subTaskOrig.getUserName().equals(userName)) {
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
      if (subTask.getSubTaskName() != null) {
        subTaskOrig.setSubTaskName(subTask.getSubTaskName());
      }
      subTaskOrig.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));
      session.update(subTaskOrig);
    } else
      throw new UnauthorizedException(
              String.format("This subtask doesn't belong to %s user", userName));
  }
}
