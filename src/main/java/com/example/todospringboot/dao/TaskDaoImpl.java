package com.example.todospringboot.dao;

import com.example.todospringboot.entity.Task;
import com.example.todospringboot.entity.TaskList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
  public List<Task> getAllTask(String userName) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Task> query = session.createQuery("from Task where userName=:userName", Task.class);
    query.setParameter("userName", userName);
    return query.getResultList();
  }

  @Override
  public void deleteTask(int taskId, String userName) {
    Task task = this.getTask(taskId);
    if (task.getUserName().equals(userName)) {
      Session session = this.sessionFactory.getCurrentSession();
      session.delete(task);
    }
    // TODO: Add custom exception when user tries to delete other users data
  }

  @Override
  public Task getTask(int taskId) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Task> query = session.createQuery("from Task where id=:taskId", Task.class);
    query.setParameter("taskId", taskId);
    return query.getSingleResult();
  }

  @Override
  public void updateTask(int taskId, Task task, String userName) {
    Task taskOrig = this.getTask(taskId);
    if (taskOrig.getUserName().equals(userName)) {
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
      taskOrig.setModifiedTimeStamp(new Timestamp(System.currentTimeMillis()));
      session.update(taskOrig);
    }
    // TODO: Add custom exception when user tries to delete other users data
  }

  @Override
  public void addTask(int taskListId, Task task) {
    Session session = this.sessionFactory.getCurrentSession();
    TaskList taskList = this.taskListDao.getTaskList(taskListId);
    task.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
    task.setTaskList(taskList);
    session.save(task);
  }

  @SuppressWarnings("JpaQlInspection")
  @Override
  public List<Task> getAllTaskFromTaskList(String userName, int taskListId) {
    Session session = this.sessionFactory.getCurrentSession();
    Query<Task> query =
            session.createQuery(
                    "from Task where task_list_id=:taskListId and userName=:userName", Task.class);
    query.setParameter("taskListId", taskListId);
    query.setParameter("userName", userName);
    return query.getResultList();
  }
}
