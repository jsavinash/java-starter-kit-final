// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/.well-known/**", "/oauth2/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form.permitAll());

        return http.build();
    }
}
