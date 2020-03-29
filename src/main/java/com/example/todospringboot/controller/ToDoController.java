package com.example.todospringboot.controller;

import com.example.todospringboot.entity.SubTask;
import com.example.todospringboot.entity.Task;
import com.example.todospringboot.entity.TaskList;
import com.example.todospringboot.exceptions.SubTaskNotFoundException;
import com.example.todospringboot.exceptions.TaskListNotFoundException;
import com.example.todospringboot.exceptions.TaskNotFoundException;
import com.example.todospringboot.service.SubTaskService;
import com.example.todospringboot.service.TaskListService;
import com.example.todospringboot.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/todo/")
public class ToDoController {

  final SubTaskService subTaskService;
  final TaskListService taskListService;
  final TaskService taskService;

  private Logger logger = Logger.getLogger(getClass().getName());

  @Autowired
  public ToDoController(
          SubTaskService subTaskService, TaskListService taskListService, TaskService taskService) {
    this.subTaskService = subTaskService;
    this.taskListService = taskListService;
    this.taskService = taskService;
  }

  @PostMapping("/addTaskList")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public void addTaskList(@RequestBody TaskList taskList) {
    taskListService.addTaskList(taskList);
  }

  @GetMapping("/getTaskList/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<TaskList> getTaskList(@PathVariable("id") int taskListId) {
    try {
      return new ResponseEntity<>(taskListService.getTaskList(taskListId), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/deleteTaskList/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteTaskList(@PathVariable("id") int taskListId) {
    try {
      taskListService.deleteTaskList(taskListId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllTaskList")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<TaskList>> getAllTaskList() {
    return new ResponseEntity<>(taskListService.getAllTaskList(), HttpStatus.OK);
  }

  @PutMapping(value = "/updateTaskList/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateTaskList(@PathVariable("id") int taskListId, @RequestBody TaskList taskList) {
    try {
      taskListService.updateTaskList(taskListId, taskList);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  // =========================================================================================

  @PostMapping("/addTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public void addTask(@PathVariable("id") int taskListId, @RequestBody Task task) {
    try {
      taskService.addTask(taskListId, task);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<Task> getTask(@PathVariable("id") int taskId) {
    try {
      return new ResponseEntity<>(taskService.getTask(taskId), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllTask")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<Task>> getAllTask() {
    return new ResponseEntity<>(taskService.getAllTask(), HttpStatus.OK);
  }

  @GetMapping("/getAllTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<Task>> getAllTaskFromTaskList(@PathVariable("id") int taskListId) {
    return new ResponseEntity<>(taskService.getAllTaskFromTaskList(taskListId), HttpStatus.OK);
  }


  @DeleteMapping("/deleteTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteTask(@PathVariable("id") int taskId) {
    try {
      taskService.deleteTask(taskId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @PutMapping(value = "/updateTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateTask(@PathVariable("id") int taskId, @RequestBody Task task) {
    try {
      taskService.updateTask(taskId, task);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }
  // =========================================================================================

  @PostMapping("/addSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public void addSubTask(@PathVariable("id") int taskId, @RequestBody SubTask subTask) {
    try {
      subTaskService.addSubTask(taskId, subTask);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<SubTask>> getAllSubTask(@PathVariable("id") int taskId) {
    return new ResponseEntity<>(subTaskService.getAllSubTask(taskId), HttpStatus.OK);
  }

  @GetMapping("/getSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<SubTask> getSubTask(@PathVariable("id") int subTaskId) {
    try {
      return new ResponseEntity<>(subTaskService.getSubTask(subTaskId), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/deleteSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteSubTask(@PathVariable("id") int subTaskId) {
    try {
      subTaskService.deleteSubTask(subTaskId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @PutMapping(value = "/updateSubTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateSubTask(@PathVariable("id") int subTaskId, @RequestBody SubTask subTask) {
    try {
      subTaskService.updateSubTask(subTaskId, subTask);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }
}
