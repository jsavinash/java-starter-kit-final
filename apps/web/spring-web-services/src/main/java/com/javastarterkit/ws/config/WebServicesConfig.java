// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ws.config;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServicesConfig implements WebMvcConfigurer {

    @Bean
    public SimpleXsdSchema helloSchema() {
        return new SimpleXsdSchema(new org.springframework.core.io.ClassPathResource("hello.xsd"));
    }

    @Bean
    public SimpleWsdl11Definition helloWsdl(XsdSchema helloSchema) {
        return new SimpleWsdl11Definition(new org.springframework.core.io.ClassPathResource("hello.wsdl"));
    }

    @Bean
    public MessageDispatcherServlet messageDispatcherServlet() {
        return new MessageDispatcherServlet() {
            @Override
            protected void registerDefaultBeans(ServletConfig servletConfig) throws ServletException {
                // Register default beans for WS
            }
        };
    }
}
