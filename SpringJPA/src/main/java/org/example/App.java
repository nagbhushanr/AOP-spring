package org.example;

import org.example.configuration.AppConfig;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
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
