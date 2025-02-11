package com.example.todospringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Table(name = "subtask")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "subtask_name")
  @NotEmpty(message = "Please provide subtask name")
  private String subTaskName;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "subtask_description")
  private String subTaskDescription;

  @Column(name = "subtask_status")
  @NotEmpty(message = "Please provide subtask status")
  private String subTaskStatus;

  @Column(name = "created_at")
  private Timestamp createdTimeStamp;

  @Column(name = "modified_at")
  private Timestamp modifiedTimeStamp;

  @Column(name = "due_date")
  private Timestamp dueDate;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id", updatable = false)
  @Fetch(FetchMode.JOIN)
  private Task task;

  public SubTask() {
  }

  public SubTask(
          String subTaskName, String userName, String subTaskDescription, String subTaskStatus) {
    this.subTaskName = subTaskName;
    this.userName = userName;
    this.subTaskDescription = subTaskDescription;
    this.subTaskStatus = subTaskStatus;
  }

  public static SubTask getDefaultInstance() {
    return new SubTask();
  }

  public String getSubTaskName() {
    return subTaskName;
  }

  public void setSubTaskName(String subTaskName) {
    this.subTaskName = subTaskName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSubTaskDescription() {
    return subTaskDescription;
  }

  public void setSubTaskDescription(String subTaskDescription) {
    this.subTaskDescription = subTaskDescription;
  }

  public String getSubTaskStatus() {
    return subTaskStatus;
  }

  public void setSubTaskStatus(String subTaskStatus) {
    this.subTaskStatus = subTaskStatus;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
    return "SubTask{"
            + "id="
            + id
            + ", subTaskName='"
            + subTaskName
            + '\''
            + ", userName='"
            + userName
            + '\''
            + ", subTaskDescription='"
            + subTaskDescription
            + '\''
            + ", subTaskStatus='"
            + subTaskStatus
            + '\''
            + '}';
  }
}
