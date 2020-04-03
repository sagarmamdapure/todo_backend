package com.example.todospringboot.service;

import com.example.todospringboot.dao.RoleRepositoryDao;
import com.example.todospringboot.entity.Role;
import com.example.todospringboot.models.ERole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleRepositoryServiceTest {

    @InjectMocks
    RoleRepositoryServiceImpl roleRepositoryService;

    @Mock
    RoleRepositoryDao roleRepositoryDao;

    @Before
    public void setUp() {
        Role role = new Role(ERole.ROLE_USER);
        when(roleRepositoryDao.findByName(any())).thenReturn(java.util.Optional.of(role));
    }

    @Test
    public void testFindByName() {
        Optional<Role> actual_data = roleRepositoryService.findByName(ERole.ROLE_USER);
        Optional<Role> expected_data = Optional.of(new Role(ERole.ROLE_USER));
        if (expected_data.isPresent() && actual_data.isPresent()) {
            assertEquals(expected_data.get().getId(), actual_data.get().getId());
            assertEquals(expected_data.get().getName(), actual_data.get().getName());
        }
    }
}
