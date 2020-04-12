package com.example.todospringboot.dao;

import com.example.todospringboot.entity.TaskList;
import com.example.todospringboot.exceptions.UnauthorizedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TaskListDaoImpl implements TaskListDao {
  final SessionFactory sessionFactory;
  private Logger logger = Logger.getLogger(getClass().getName());

  public TaskListDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public List<TaskList> getAllTaskList(String userName) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<TaskList> theQuery =
            session.createQuery("from TaskList where userName=:userName", TaskList.class);
    theQuery.setParameter("userName", userName);
    return theQuery.getResultList();
  }

  @Override
  public void deleteTaskList(int taskListId, String userName) {
    TaskList taskList = this.getTaskList(taskListId, userName);
    if (taskList.getUserName().equals(userName)) {
      Session session = this.sessionFactory.getCurrentSession();
      session.delete(taskList);
    } else throw new UnauthorizedException(String.format("This tasklist doesn't belong to %s user", userName));
  }

  @Override
  public TaskList getTaskList(int taskListId, String userName) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<TaskList> query =
            session.createQuery("from TaskList where id=:taskListId and userName=:userName", TaskList.class);
    query.setParameter("taskListId", taskListId);
    query.setParameter("userName", userName);
    return query.getSingleResult();
  }

  @Override
  public void updateTaskList(int taskListId, TaskList taskList, String userName) {
    TaskList taskListOrig = this.getTaskList(taskListId, userName);
    if (taskListOrig.getUserName().equals(userName)) {
      Session session = this.sessionFactory.getCurrentSession();
      if (taskList.getTaskListName() != null) {
        taskListOrig.setTaskListName(taskList.getTaskListName());
      }
      if (taskList.getTasks() != null) {
        taskListOrig.setTasks(taskList.getTasks());
      }
      if (taskList.getUserName() != null) {
        taskListOrig.setUserName(taskList.getUserName());
      }
      taskListOrig.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));
      session.update(taskListOrig);
    } else throw new UnauthorizedException(String.format("This tasklist doesn't belong to %s user", userName));
  }

  @Override
  public void addTaskList(TaskList taskList) {
    Session session = this.sessionFactory.getCurrentSession();
    taskList.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
    session.save(taskList);
  }
}
