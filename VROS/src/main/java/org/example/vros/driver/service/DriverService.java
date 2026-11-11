package org.example.vros.driver.service;

import org.example.vros.driver.entity.Driver;
import org.example.vros.driver.repository.DriverRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository repository;

    public DriverService(DriverRepository repository) { this.repository = repository; }
    public List<Driver> findAll() { return repository.findAll(); }
}