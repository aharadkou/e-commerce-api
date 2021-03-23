package com.epam.training.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:security.properties")
@ConditionalOnProperty(value = "security-enabled", matchIfMissing = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${admin.userName}")
    private String admin;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.role}")
    private String adminRole;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(admin)
                    .password(encoder().encode(adminPassword)).roles(adminRole);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
       http.authorizeRequests()
                .anyRequest()
                    .authenticated()
                        .and()
                            .httpBasic()
                                .and()
                                    .csrf()
                                        .disable()
                                            .cors();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
