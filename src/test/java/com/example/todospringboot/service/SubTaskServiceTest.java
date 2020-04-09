package com.example.todospringboot.service;

import com.example.todospringboot.dao.SubTaskDao;
import com.example.todospringboot.entity.SubTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubTaskServiceTest {

  @Mock
  SubTaskDao subTaskDao;

  @InjectMocks
  SubTaskServiceImpl subTaskService;

  @Before
  public void setUp() {
    List<SubTask> testData = getTestData();
    when(subTaskDao.getAllSubTask(anyString(), anyInt())).thenReturn(testData);
    when(subTaskDao.getSubTask(anyInt())).thenReturn(testData.get(0));
  }

  private List<SubTask> getTestData() {
    SubTask subTask1 =
            new SubTask(
                    "test_subTaskName_1",
                    "test_userName_1",
                    "test_subTaskDescription_1",
                    "test_subTaskStatus_1");
    SubTask subTask2 =
            new SubTask(
                    "test_subTaskName_2",
                    "test_userName_2",
                    "test_subTaskDescription_2",
                    "test_subTaskStatus_2");
    ArrayList<SubTask> subTasks = new ArrayList<>();
    subTasks.add(subTask1);
    subTasks.add(subTask2);
    return subTasks;
  }

  @Test
  public void testGetAllSubTask() {
    List<SubTask> expected_data = getTestData();
    List<SubTask> actual_data = subTaskService.getAllSubTask("dummy_username", 1);
    assertEquals(expected_data.size(), actual_data.size());
    assertEquals(expected_data.get(0).getId(), actual_data.get(0).getId());
    assertEquals(expected_data.get(0).getUserName(), actual_data.get(0).getUserName());
    assertEquals(
            expected_data.get(0).getSubTaskDescription(), actual_data.get(0).getSubTaskDescription());
    assertEquals(expected_data.get(0).getSubTaskStatus(), actual_data.get(0).getSubTaskStatus());
    assertEquals(expected_data.get(0).getTask(), actual_data.get(0).getTask());

    assertEquals(expected_data.get(1).getId(), actual_data.get(1).getId());
    assertEquals(expected_data.get(1).getUserName(), actual_data.get(1).getUserName());
    assertEquals(
            expected_data.get(1).getSubTaskDescription(), actual_data.get(1).getSubTaskDescription());
    assertEquals(expected_data.get(1).getSubTaskStatus(), actual_data.get(1).getSubTaskStatus());
    assertEquals(expected_data.get(1).getTask(), actual_data.get(1).getTask());
  }

  @Test
  public void testGetSubTask() {
    SubTask expected_data = getTestData().get(0);

    SubTask actual_data = subTaskService.getSubTask(1);
    assertEquals(expected_data.getId(), actual_data.getId());
    assertEquals(expected_data.getTask(), actual_data.getTask());
    assertEquals(expected_data.getSubTaskStatus(), actual_data.getSubTaskStatus());
    assertEquals(expected_data.getSubTaskDescription(), actual_data.getSubTaskDescription());
    assertEquals(expected_data.getUserName(), actual_data.getUserName());
    assertEquals(expected_data.getSubTaskName(), actual_data.getSubTaskName());
  }

  @Test
  public void testDeleteSubTask() {
    subTaskService.deleteSubTask(1);
  }

  @Test
  public void testAddSubTask() {
    subTaskService.addSubTask(1, SubTask.getDefaultInstance());
  }

  @Test
  public void testUpdateSubTask() {
    subTaskService.updateSubTask(1, SubTask.getDefaultInstance());
  }
}
