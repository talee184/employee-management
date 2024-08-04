package com.talee.employee.management.service;

import com.talee.employee.management.model.Users;
import com.talee.employee.management.model.UserPrincipal;
import com.talee.employee.management.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public Users saveUser(Users users) {
    users.setPassword(encoder.encode(users.getPassword()));
    return userRepo.save(users);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users = userRepo.findByUsername(username);
    if (users == null) {
      throw new UsernameNotFoundException("Error 404");
    } else {
      return new UserPrincipal(users);
    }
  }
}
