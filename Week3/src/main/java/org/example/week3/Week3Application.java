package org.example.week3;

import org.example.week3.injection.InjectionDemo;
import org.example.week3.library.service.BorrowingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.example.week3.library.config.LibraryProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LibraryProperties.class)
public class Week3Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(
                        Week3Application.class,
                        args
                );

        InjectionDemo demo =
                context.getBean(InjectionDemo.class);

        demo.printMessages();

        context.close();
    }

    @Bean
    CommandLineRunner libraryDemo(
            BorrowingService borrowingService) {

        return args -> {

            System.out.println(
                    "Selected policy: "
                            + borrowingService
                            .getBorrowingPolicy()
                            .getPolicyName()
            );

            borrowingService.borrowBook(1L, 1L);
            borrowingService.borrowBook(1L, 2L);

            borrowingService.returnBook(1L, 1L);

            try {
                borrowingService.borrowBook(1L, 1L);

                borrowingService.borrowBook(1L, 1L);
            } catch (Exception exception) {

                System.out.println(
                        "MAIN: operation failed - "
                                + exception.getMessage()
                );
            }
        };
    }
}