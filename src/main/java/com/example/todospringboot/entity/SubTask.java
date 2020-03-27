package com.example.todospringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "subtask")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "subtask_name")
  private String subTaskName;

  @Column(name = "user_name")
  private String userName;

  @Column(name = "subtask_description")
  private String subTaskDescription;

  @Column(name = "subtask_status")
  private String subTaskStatus;

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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
