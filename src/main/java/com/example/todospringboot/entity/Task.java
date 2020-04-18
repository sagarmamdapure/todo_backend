package com.example.todospringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "task_name")
  @NotEmpty(message = "Please provide task name")
  private String taskName;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "task_description")
  @NotEmpty(message = "Please provide task description")
  private String taskDescription;

  @Column(name = "task_status")
  @NotEmpty(message = "Please provide task status")
  private String taskStatus;

  @Column(name = "created_at")
  private Timestamp createdTimeStamp;

  @Column(name = "modified_at")
  private Timestamp modifiedTimeStamp;

  @Column(name = "due_date")
  private Timestamp dueDate;

  @JsonIgnore
  @OneToMany(
          targetEntity = SubTask.class,
          mappedBy = "task",
          cascade = CascadeType.ALL,
          fetch = FetchType.LAZY)
  private List<SubTask> subTasks;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_list_id", updatable = false)
  @Fetch(FetchMode.JOIN)
  private TaskList taskList;

  public Task() {
  }

  public Task(String taskName, String userName, String taskDescription, String taskStatus) {
    this.taskName = taskName;
    this.userName = userName;
    this.taskDescription = taskDescription;
    this.taskStatus = taskStatus;
  }

  public static Task getDefaultInstance() {
    return new Task();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public TaskList getTaskList() {
    return taskList;
  }

  public void setTaskList(TaskList taskList) {
    this.taskList = taskList;
  }

  public List<SubTask> getSubTasks() {
    return subTasks;
  }

  public void setSubTasks(List<SubTask> subTasks) {
    this.subTasks = subTasks;
  }

  public void add(SubTask subTask) {
    if (subTasks == null) {
      subTasks = new ArrayList<>();
    }
    subTasks.add(subTask);
    subTask.setTask(this);
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public String getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
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
    return "Task{"
            + "id="
            + id
            + ", taskName='"
            + taskName
            + '\''
            + ", userName='"
            + userName
            + '\''
            + ", taskDescription='"
            + taskDescription
            + '\''
            + ", taskStatus='"
            + taskStatus
            + '\''
            + '}';
  }
}
