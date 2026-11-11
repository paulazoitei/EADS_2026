package org.example.vros.customer.service;

import org.example.vros.customer.entity.Customer;
import org.example.vros.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }


    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }


    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}