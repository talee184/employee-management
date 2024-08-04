package com.talee.employee.management.filter;

import com.talee.employee.management.service.JwtService;
import com.talee.employee.management.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  HandlerExceptionResolver handlerExceptionResolver;

  // Method to lazily fetch the UserService bean from the ApplicationContext
  // This is done to avoid Circular Dependency issues
  private UserService getUserService() {
    return applicationContext.getBean(UserService.class);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // Extracting token from the request header
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = null;
    String userName = null;
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        // Extracting the token from the Authorization header
        token = authHeader.substring(7);
        // Extracting username from the token
        userName = jwtService.extractUserName(token);
      }
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      // If username is extracted and there is no authentication in the current SecurityContext
      if (userName != null && authentication == null) {
        // Loading UserDetails by username extracted from the token
        UserDetails userDetails = getUserService().loadUserByUsername(userName);

        // Validating the token with loaded UserDetails
        if (jwtService.validateToken(token, userDetails)) {
          // Creating an authentication token using UserDetails
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          // Setting authentication details
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          // Setting the authentication token in the SecurityContext
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      // Proceeding with the filter chain
      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }
}
