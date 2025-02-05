package org.example.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"org.example.entity","org.example.repository",
		"org.example.service"})
@Import(MongoConfig.class)
public class AppConfig {
}
