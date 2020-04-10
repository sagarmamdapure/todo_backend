package com.example.todospringboot.dao;

import com.example.todospringboot.entity.TaskList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

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
    TaskList taskList = this.getTaskList(taskListId);
    if (taskList.getUserName().equals(userName)) {
      Session session = this.sessionFactory.getCurrentSession();
      session.delete(taskList);
    }
    // TODO: Add custom exception when user tries to delete other users data
  }

  @Override
  public TaskList getTaskList(int taskListId) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<TaskList> query =
            session.createQuery("from TaskList where id=:taskListId", TaskList.class);
    query.setParameter("taskListId", taskListId);
    return query.getSingleResult();
  }

  @Override
  public void updateTaskList(int taskListId, TaskList taskList, String userName) {
    TaskList taskListOrig = this.getTaskList(taskListId);
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
      session.update(taskListOrig);
    }
    // TODO: Add custom exception when user tries to delete other users data
  }

  @Override
  public void addTaskList(TaskList taskList) {
    Session session = this.sessionFactory.getCurrentSession();
    session.save(taskList);
  }
}
