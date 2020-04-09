package com.example.todospringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasklist")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "task_list_name")
  private String taskListName;

  @Column(name = "user_name")
  private String userName;

  @JsonIgnore
  @OneToMany(
          targetEntity = Task.class,
          cascade = CascadeType.ALL,
          mappedBy = "taskList",
          fetch = FetchType.LAZY)
  private List<Task> tasks;

  public TaskList(String taskListNameArg, String userNameArg) {
    taskListName = taskListNameArg;
    userName = userNameArg;
  }

  public TaskList() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public static TaskList getDefaultInstance() {
    return new TaskList();
  }

  public void add(Task task) {
    if (tasks == null) {
      tasks = new ArrayList<>();
    }
    tasks.add(task);
    task.setTaskList(this);
  }

  public String getTaskListName() {
    return taskListName;
  }

  public void setTaskListName(String taskListName) {
    this.taskListName = taskListName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "TaskList{"
            + "id="
            + id
            + ", taskListName='"
            + taskListName
            + '\''
            + ", userName='"
            + userName
            + '\''
            + '}';
  }
}
