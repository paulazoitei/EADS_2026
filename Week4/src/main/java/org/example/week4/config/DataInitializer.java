package org.example.week4.config;

import org.example.week4.customer.entity.Customer;
import org.example.week4.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner testCustomerRepository(
            CustomerRepository customerRepository
    ) {
        return args -> {

            Customer customer = customerRepository
                    .findByEmail("ion.popescu@example.com")
                    .orElseGet(() -> {
                        Customer newCustomer = new Customer(
                                "Ion Popescu",
                                "ion.popescu@example.com",
                                "0712345678",
                                "Strada Libertatii nr. 10, Bucuresti",
                                "44.4268, 26.1025"
                        );

                        return customerRepository.save(newCustomer);
                    });

            System.out.println("Customer salvat:");
            System.out.println(customer);

            System.out.println("Numar total clienti: "
                    + customerRepository.count());

            System.out.println("Toti clientii:");

            customerRepository.findAll()
                    .forEach(System.out::println);
        };
    }
}