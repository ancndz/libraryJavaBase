package ru.ancndz.libraryBase.libraryEnvironment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.ancndz.libraryBase.SpringConfig;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        this.context = new AnnotationConfigApplicationContext(SpringConfig.class);
    }

    @Test
    void createTest() {
        Library library1 = context.getBean("library", Library.class);
        System.out.println(library1.getId());
    }
}