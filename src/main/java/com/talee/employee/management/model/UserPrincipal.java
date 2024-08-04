package com.talee.employee.management.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  String userName = null;
  String password = null;
  Set<SimpleGrantedAuthority> authorities;

  public UserPrincipal(Users user) {
    userName = user.getUsername();
    password = user.getPassword();
    authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
