package com.HaikalArif.project_management_api.Config;

import com.HaikalArif.project_management_api.Service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Disable CSRF for simplicity
                .authorizeRequests()
                .antMatchers("/projects/{projectId}/tasks/**").hasRole("USER") // Only Users can access this endpoint
                .antMatchers("/projects/**").hasRole("ADMIN") // Only Admins can access this endpoint
                .antMatchers("/test/api/admin").hasRole("ADMIN")
                .antMatchers("/test/api/user").hasRole("USER")
                .antMatchers("/users/**").permitAll()// All can access this endpoint
                .antMatchers("/auth/register").permitAll() // All can access this endpoint
                .anyRequest().permitAll()
                .and()
                .httpBasic()  // Enable Basic Authentication
                .and()
                .formLogin().disable();  // Disable form login
    }

    // Authenticate using User information such as Email, Password and Role
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
