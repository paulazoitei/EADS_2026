package org.example.vros.depot.service;

import org.example.vros.depot.entity.Depot;
import org.example.vros.depot.repository.DepotRepository;
import org.springframework.stereotype.Service;

@Service
public class DepotService {
    private final DepotRepository repository;
    public DepotService(DepotRepository repository) { this.repository = repository; }
}