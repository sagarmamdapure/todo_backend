package com.example.todospringboot.service;

import com.example.todospringboot.dao.UserRepositoryDao;
import com.example.todospringboot.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryServiceTest {

    @InjectMocks
    UserRepositoryServiceImpl userRepositoryService;

    @Mock
    UserRepositoryDao userRepositoryDao;

    @Before
    public void setUp() {
        Optional<User> user =
                Optional.of(
                        new User(
                                "test_username", "test_email", "test_password", "test_firstName", "test_lastName"));
        when(userRepositoryDao.findByUsername(anyString())).thenReturn(user);
        when(userRepositoryDao.existsByEmail(anyString())).thenReturn(Boolean.TRUE);
        when(userRepositoryDao.existsByUsername(anyString())).thenReturn(Boolean.TRUE);
    }

    @Test
    public void testFindByUsername() {
        Optional<User> expected_data =
                Optional.of(
                        new User(
                                "test_username", "test_email", "test_password", "test_firstName", "test_lastName"));
        Optional<User> actual_data = userRepositoryService.findByUsername("test_user");
        if (expected_data.isPresent() && actual_data.isPresent()) {
            assertEquals(expected_data.get().getId(), actual_data.get().getId());
            assertEquals(expected_data.get().getEmail(), actual_data.get().getEmail());
            assertEquals(expected_data.get().getPassword(), actual_data.get().getPassword());
            assertEquals(expected_data.get().getUsername(), actual_data.get().getUsername());
            assertEquals(expected_data.get().getRoles(), actual_data.get().getRoles());
            assertEquals(expected_data.get().getFirstName(), actual_data.get().getFirstName());
            assertEquals(expected_data.get().getLastName(), actual_data.get().getLastName());
        }
    }

    @Test
    public void testExistsByUsername() {
        Boolean actual_response = userRepositoryService.existsByUsername("test_username");
        assertEquals(Boolean.TRUE, actual_response);
    }

    @Test
    public void testExistsByEmail() {
        Boolean actual_response = userRepositoryService.existsByEmail("test_email");
        assertEquals(Boolean.TRUE, actual_response);
    }

    @Test
    public void testSave() {
        userRepositoryService.save(User.getDefaultInstance());
    }
}
