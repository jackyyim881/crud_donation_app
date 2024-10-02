// // src/main/java/com/example/demo/config/SecurityConfig.java
// package com.donation.config;

// import com.donation.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// // Import other necessary classes
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.*;
// import org.springframework.security.core.userdetails.*;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import java.util.stream.Collectors;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {

// @Autowired
// private UserService userService;

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @Override
// protected void configure(AuthenticationManagerBuilder auth) throws Exception
// {
// auth.userDetailsService(new UserDetailsService() {
// @Override
// public UserDetails loadUserByUsername(String username) throws
// UsernameNotFoundException {
// com.example.demo.model.User user = userService.findByUsername(username);
// if (user == null) {
// throw new UsernameNotFoundException("User not found");
// }
// return new org.springframework.security.core.userdetails.User(
// user.getUsername(),
// user.getPassword(),
// user.isEnabled(),
// true,
// true,
// true,
// user.getRoles().stream()
// .map(role -> new SimpleGrantedAuthority(role.getName()))
// .collect(Collectors.toList()));
// }
// }).passwordEncoder(passwordEncoder());
// }

// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http
// .csrf().disable() // Enable CSRF protection in production
// .authorizeRequests()
// .antMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
// .antMatchers("/api/v1/**").authenticated() // Secure API endpoints
// .antMatchers("/admin/**").hasRole("ADMIN") // Example for admin routes
// .anyRequest().authenticated()
// .and()
// .formLogin()
// .loginPage("/login") // Custom login page
// .defaultSuccessUrl("/home", true)
// .permitAll()
// .and()
// .logout()
// .permitAll();
// }
// }
