package com.gmail.artemkrotenok.web.config;

import com.gmail.artemkrotenok.service.impl.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.gmail.artemkrotenok.repository.model.UserRoleEnum.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(
            AppUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/webjars/**").permitAll()
                .antMatchers("/home").hasAnyRole(ADMINISTRATOR.name(), SALE_USER.name(), CUSTOMER_USER.name(), SECURE_API_USER.name())
                .antMatchers("/users**").hasAnyRole(ADMINISTRATOR.name(),SALE_USER.name(), CUSTOMER_USER.name(),SECURE_API_USER.name())
                .antMatchers("/users/profile").hasAnyRole(CUSTOMER_USER.name())
                .antMatchers("/feedback**").hasAnyRole(ADMINISTRATOR.name())
                .antMatchers("/news").hasAnyRole(SALE_USER.name(), CUSTOMER_USER.name())
                .antMatchers("/news/add").hasAnyRole(SALE_USER.name())
                .antMatchers("/orders").hasAnyRole(SALE_USER.name(), CUSTOMER_USER.name())
                .antMatchers("/items").hasAnyRole(ADMINISTRATOR.name(),SALE_USER.name(), CUSTOMER_USER.name(),SECURE_API_USER.name())
                .antMatchers("/items/upload").hasAnyRole(ADMINISTRATOR.name(),SALE_USER.name(), CUSTOMER_USER.name(),SECURE_API_USER.name())
                .antMatchers("/api/*").hasAnyRole(SECURE_API_USER.name())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

}
