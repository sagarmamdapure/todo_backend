package com.example.todospringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasklist")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "task_list_name")
  @NotEmpty(message = "Please provide tasklist name")
  private String taskListName;


  @Column(name = "user_name")
  private String userName;

  @Column(name = "created_at")
  private Timestamp createdTimeStamp;

  @Column(name = "modified_at")
  private Timestamp modifiedTimeStamp;

  @Column(name = "due_date")
  private Timestamp dueDate;

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

  public static TaskList getDefaultInstance() {
    return new TaskList();
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

  public Timestamp getCreatedTimeStamp() {
    return createdTimeStamp;
  }

  public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
    this.createdTimeStamp = createdTimeStamp;
  }

  public Timestamp getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(Timestamp modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public Timestamp getDueDate() {
    return dueDate;
  }

  public void setDueDate(Timestamp dueDate) {
    this.dueDate = dueDate;
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
