package org.example;

import org.example.configuration.AppConfig;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ImportResource("classpath:/spring-servlet.xml")
public class App  extends SpringBootServletInitializer
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        try{
            User user = userService.getUserByName("raghav");
            System.out.println(user.getName());
        }catch(Exception e) {
            System.out.println("Something went wrong please check logs");
        }
    }
}
