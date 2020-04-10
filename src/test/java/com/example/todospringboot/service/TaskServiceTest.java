package com.example.todospringboot.service;

import com.example.todospringboot.dao.TaskDao;
import com.example.todospringboot.entity.Task;
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
public class TaskServiceTest {

    @Mock
    TaskDao taskDao;

    @InjectMocks
    TaskServiceImpl taskService;

    @Before
    public void setUp() {
        List<Task> testData = getTestData();
        when(taskDao.getAllTask(anyString())).thenReturn(testData);
        when(taskDao.getTask(anyInt())).thenReturn(testData.get(0));
        when(taskDao.getAllTaskFromTaskList(anyString(), anyInt())).thenReturn(testData);
    }

    @Test
    public void testGetAllTask() {
        List<Task> tasks = taskService.getAllTask("test_user");
        assertEquals(getTestData().size(), tasks.size());
    }

    private List<Task> getTestData() {
        Task task1 =
                new Task(
                        "test_taskName_1", "test_userName_1", "test_taskDescription_1", "test_taskStatus_1");
        Task task2 =
                new Task(
                        "test_taskName_2", "test_userName_2", "test_taskDescription_2", "test_taskStatus_2");

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        return tasks;
    }

    @Test
    public void testDeleteTask() {
        taskService.deleteTask(1, "test_user");
    }

    @Test
    public void testGetTask() {
        List<Task> expectedData = getTestData();
        Task task = taskService.getTask(1);
        assertEquals(expectedData.get(0).getId(), task.getId());
        assertEquals(expectedData.get(0).getUserName(), task.getUserName());
        assertEquals(expectedData.get(0).getTaskStatus(), task.getTaskStatus());
        assertEquals(expectedData.get(0).getTaskDescription(), task.getTaskDescription());
        assertEquals(expectedData.get(0).getTaskName(), task.getTaskName());
    }

    @Test
    public void testUpdateTask() {
        taskService.updateTask(1, Task.getDefaultInstance(), "test_user");
    }

    @Test
    public void testAddTask() {
        taskService.addTask(1, Task.getDefaultInstance());
    }

    @Test
    public void testGetAllTaskFromTaskList() {
        List<Task> expected_data = getTestData();
        List<Task> actual_data = taskService.getAllTaskFromTaskList("dummy_username", 1);
        assertEquals(expected_data.size(), actual_data.size());
        assertEquals(expected_data.get(0).getId(), actual_data.get(0).getId());
        assertEquals(expected_data.get(0).getTaskName(), actual_data.get(0).getTaskName());
        assertEquals(
                expected_data.get(0).getTaskDescription(), actual_data.get(0).getTaskDescription());
        assertEquals(expected_data.get(0).getTaskStatus(), actual_data.get(0).getTaskStatus());
        assertEquals(expected_data.get(0).getTaskList(), actual_data.get(0).getTaskList());
        assertEquals(expected_data.get(0).getTaskList(), actual_data.get(0).getTaskList());
        assertEquals(expected_data.get(0).getSubTasks(), actual_data.get(0).getSubTasks());

        assertEquals(expected_data.get(1).getTaskName(), actual_data.get(1).getTaskName());
        assertEquals(
                expected_data.get(1).getTaskDescription(), actual_data.get(1).getTaskDescription());
        assertEquals(expected_data.get(1).getTaskStatus(), actual_data.get(1).getTaskStatus());
        assertEquals(expected_data.get(1).getTaskList(), actual_data.get(1).getTaskList());
        assertEquals(expected_data.get(1).getTaskList(), actual_data.get(1).getTaskList());
        assertEquals(expected_data.get(1).getSubTasks(), actual_data.get(1).getSubTasks());
    }
}
