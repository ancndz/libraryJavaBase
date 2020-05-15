package ru.ancndz.libraryBase;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.ancndz.libraryBase")
@PropertySource("classpath:libraryTest.properties")
public class SpringConfig {
}
