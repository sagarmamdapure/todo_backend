package com.example.todospringboot.security;

import com.example.todospringboot.dao.UserRepositoryDao;
import com.example.todospringboot.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  final UserRepositoryDao userRepository;

  public UserDetailsServiceImpl(UserRepositoryDao userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
            userRepository
                    .findByUsername(username)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }
}
