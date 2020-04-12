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
import org.springframework.security.core.Authentication;
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
  public void addTaskList(@RequestBody TaskList taskList, Authentication authentication) {
    taskList.setUserName(authentication.getName());
    taskListService.addTaskList(taskList);
  }

  @GetMapping("/getTaskList/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<TaskList> getTaskList(
          @PathVariable("id") int taskListId, Authentication authentication) {
    try {
      return new ResponseEntity<>(
              taskListService.getTaskList(taskListId, authentication.getName()), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/deleteTaskList/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteTaskList(
          @PathVariable("id") int taskListId, Authentication authentication) {
    try {
      taskListService.deleteTaskList(taskListId, authentication.getName());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllTaskList")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<TaskList>> getAllTaskList(Authentication authentication) {
    return new ResponseEntity<>(
            taskListService.getAllTaskList(authentication.getName()), HttpStatus.OK);
  }

  @PutMapping(value = "/updateTaskList/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateTaskList(
          @PathVariable("id") int taskListId,
          @RequestBody TaskList taskList,
          Authentication authentication) {
    try {
      taskListService.updateTaskList(taskListId, taskList, authentication.getName());
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  // =========================================================================================

  @PostMapping("/addTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public void addTask(
          @PathVariable("id") int taskListId, @RequestBody Task task, Authentication authentication) {
    try {
      task.setUserName(authentication.getName());
      taskService.addTask(taskListId, task);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskListNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<Task> getTask(
          @PathVariable("id") int taskId, Authentication authentication) {
    try {
      return new ResponseEntity<>(
              taskService.getTask(taskId, authentication.getName()), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllTask")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<Task>> getAllTask(Authentication authentication) {
    return new ResponseEntity<>(taskService.getAllTask(authentication.getName()), HttpStatus.OK);
  }

  @GetMapping("/getAllTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<Task>> getAllTaskFromTaskList(
          @PathVariable("id") int taskListId, Authentication authentication) {
    return new ResponseEntity<>(
            taskService.getAllTaskFromTaskList(authentication.getName(), taskListId), HttpStatus.OK);
  }

  @DeleteMapping("/deleteTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteTask(
          @PathVariable("id") int taskId, Authentication authentication) {
    try {
      taskService.deleteTask(taskId, authentication.getName());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @PutMapping(value = "/updateTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateTask(
          @PathVariable("id") int taskId, @RequestBody Task task, Authentication authentication) {
    try {
      taskService.updateTask(taskId, task, authentication.getName());
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }
  // =========================================================================================

  @PostMapping("/addSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public void addSubTask(
          @PathVariable("id") int taskId, @RequestBody SubTask subTask, Authentication authentication) {
    try {
      subTask.setUserName(authentication.getName());
      subTaskService.addSubTask(taskId, subTask);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new TaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @GetMapping("/getAllSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<List<SubTask>> getAllSubTask(
          Authentication authentication, @PathVariable("id") int taskId) {
    return new ResponseEntity<>(
            subTaskService.getAllSubTask(authentication.getName(), taskId), HttpStatus.OK);
  }

  @GetMapping("/getSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  @ResponseBody
  public ResponseEntity<SubTask> getSubTask(
          @PathVariable("id") int subTaskId, Authentication authentication) {
    try {
      return new ResponseEntity<>(
              subTaskService.getSubTask(subTaskId, authentication.getName()), HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @DeleteMapping("/deleteSubTask/{id}")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> deleteSubTask(
          @PathVariable("id") int subTaskId, Authentication authentication) {
    try {
      subTaskService.deleteSubTask(subTaskId, authentication.getName());
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }

  @PutMapping(value = "/updateSubTask/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public void updateSubTask(
          @PathVariable("id") int subTaskId,
          @RequestBody SubTask subTask,
          Authentication authentication) {
    try {
      subTaskService.updateSubTask(subTaskId, subTask, authentication.getName());
    } catch (EmptyResultDataAccessException e) {
      logger.log(Level.SEVERE, e.getLocalizedMessage());
      throw new SubTaskNotFoundException(e.getLocalizedMessage());
    }
  }
}
