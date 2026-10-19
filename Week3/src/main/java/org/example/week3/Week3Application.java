package org.example.week3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Week3Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(Week3Application.class, args);

        InjectionDemo demo = context.getBean(InjectionDemo.class);
        demo.printMessages();

        context.close();
    }
}