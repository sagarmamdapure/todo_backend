package com.example.todospringboot.dao;

import com.example.todospringboot.entity.Task;
import com.example.todospringboot.entity.TaskList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class TaskDaoImpl implements TaskDao {

  final SessionFactory sessionFactory;
  final TaskListDao taskListDao;
  private Logger logger = Logger.getLogger(getClass().getName());

  public TaskDaoImpl(SessionFactory sessionFactory, TaskListDao taskListDao) {
    this.sessionFactory = sessionFactory;
    this.taskListDao = taskListDao;
  }

  @Override
  public List<Task> getAllTask() {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Task> query = session.createQuery("from Task", Task.class);
    return query.getResultList();
  }

  @Override
  public void deleteTask(int taskId) {
    Task task = this.getTask(taskId);
    Session session = this.sessionFactory.getCurrentSession();
    session.delete(task);
  }

  @Override
  public Task getTask(int taskId) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Task> query = session.createQuery("from Task where id=:taskId", Task.class);
    query.setParameter("taskId", taskId);
    return query.getSingleResult();
  }

  @Override
  public void updateTask(int taskId, Task task) {
    Task taskOrig = this.getTask(taskId);
    Session session = this.sessionFactory.getCurrentSession();
    if (task.getTaskList() != null) {
      taskOrig.setTaskList(task.getTaskList());
    }
    if (task.getTaskDescription() != null) {
      taskOrig.setTaskDescription(task.getTaskDescription());
    }
    if (task.getSubTasks() != null) {
      taskOrig.setSubTasks(task.getSubTasks());
    }
    if (task.getTaskName() != null) {
      taskOrig.setTaskName(task.getTaskName());
    }
    if (task.getTaskStatus() != null) {
      taskOrig.setTaskStatus(task.getTaskStatus());
    }
    if (task.getUserName() != null) {
      taskOrig.setUserName(task.getUserName());
    }
    session.update(taskOrig);
  }

  @Override
  public void addTask(int taskListId, Task task) {
    Session session = this.sessionFactory.getCurrentSession();
    TaskList taskList = this.taskListDao.getTaskList(taskListId);
    task.setTaskList(taskList);
    session.save(task);
  }
}
