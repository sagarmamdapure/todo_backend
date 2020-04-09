package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskListDao;
import com.example.todospringboot.entity.TaskList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskListServiceTest {

  @Mock
  TaskListDao taskListDao;

  @InjectMocks
  TaskListServiceImpl taskListService;

  @Before
  public void setUp() {
    ArrayList<TaskList> testData = getTestData();
    when(taskListDao.getAllTaskList(any())).thenReturn(testData);
    when(taskListDao.getTaskList(anyInt())).thenReturn(testData.get(0));
  }

  @Test
  public void testGetAllTaskList() {
    List<TaskList> taskLists = taskListService.getAllTaskList("test_user");
    assertEquals(getTestData().size(), taskLists.size());
  }

  @Test
  public void testDeleteTaskList() {
    taskListService.deleteTaskList(1);
  }

  @Test
  public void testGetTaskList() {
    ArrayList<TaskList> expectedData = getTestData();
    assertEquals(
            expectedData.get(0).getTaskListName(), taskListService.getTaskList(0).getTaskListName());
    assertEquals(expectedData.get(0).getUserName(), taskListService.getTaskList(0).getUserName());
    assertEquals(expectedData.get(0).getId(), taskListService.getTaskList(0).getId());
  }

  @Test
  public void testAddTaskList() {
    TaskList taskList = new TaskList("test_name", "test_user");
    taskListService.addTaskList(taskList);
  }

  @Test
  public void testUpdateTaskList() {
    TaskList taskList = new TaskList("test_name", "test_user");
    taskListService.updateTaskList(1, taskList);
  }

  private ArrayList<TaskList> getTestData() {
    TaskList taskList = new TaskList("test_name", "test_user");
    ArrayList<TaskList> taskLists = new ArrayList<>();
    taskLists.add(taskList);
    return taskLists;
  }
}
