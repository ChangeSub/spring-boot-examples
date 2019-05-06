package com.alex.config;


import com.alex.service.FileService;
import com.alex.service.UserService;
import com.alex.service.impl.FileWSImpl;
import com.alex.service.impl.UserServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
    @Bean // 初始化cxf的servlet
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new CXFServlet(), "/alex/*");
    }
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
        endpoint.publish("/user");
        return endpoint;
    }

//    --------------

    @Bean
    public FileService fileService() {
        return new FileWSImpl();
    }

    @Bean
    public Endpoint filePoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), fileService());
        endpoint.publish("/file");
        return endpoint;
    }


}
