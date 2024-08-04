package com.talee.employee.management.controller;

import com.talee.employee.management.model.Users;
import com.talee.employee.management.service.JwtService;
import com.talee.employee.management.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public UserController(UserService userService, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/add-user")
  public String addUser(@RequestBody Users user) {
    return userService.addUser(user);
  }

  @PostMapping("/login")
  public String login(@RequestBody Users user) {
    //Authenticate the user
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    // if authentication is successful, generate and return a JWT token
    if (authentication.isAuthenticated()) {
      return jwtService.generateToken(user.getUsername());
    } else {
      throw new UsernameNotFoundException("Invalid Username or Password");
    }
  }
}
