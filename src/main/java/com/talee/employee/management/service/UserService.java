package com.talee.employee.management.service;

import com.talee.employee.management.model.UserPrincipal;
import com.talee.employee.management.model.Users;
import com.talee.employee.management.repo.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;


  // Adds a new user to the repository and encrypting password before saving it.
  public String addUser(Users user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepo.save(user);
    return "user added successfully";
  }

  // Loads a user's details given their userName.
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> user = userRepo.findByUsername(username);
    return user.map(UserPrincipal::new)
        .orElseThrow(() -> new UsernameNotFoundException("UserName not found: " + username));
  }
}
