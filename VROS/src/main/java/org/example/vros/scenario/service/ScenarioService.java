package org.example.vros.scenario.service;

import org.example.vros.common.exception.ResourceNotFoundException;
import org.example.vros.delivery.entity.Delivery;
import org.example.vros.delivery.repository.DeliveryRepository;
import org.example.vros.scenario.dto.ScenarioDTO;
import org.example.vros.scenario.entity.Scenario;
import org.example.vros.scenario.repository.ScenarioRepository;
import org.example.vros.vehicle.entity.Vehicle;
import org.example.vros.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final VehicleRepository vehicleRepository;
    private final DeliveryRepository deliveryRepository;

    public ScenarioService(ScenarioRepository scenarioRepository, VehicleRepository vehicleRepository, DeliveryRepository deliveryRepository) {
        this.scenarioRepository = scenarioRepository;
        this.vehicleRepository = vehicleRepository;
        this.deliveryRepository = deliveryRepository;
    }

    public List<Scenario> getAllScenarios() { return scenarioRepository.findAll(); }

    public Scenario getScenarioById(Long id) {
        return scenarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Scenariu negăsit: " + id));
    }

    @Transactional
    public Scenario createScenario(ScenarioDTO dto) {
        Scenario s = new Scenario();
        s.setName(dto.getName());
        populateAssociations(s, dto);
        return scenarioRepository.save(s);
    }

    @Transactional
    public Scenario updateScenario(Long id, ScenarioDTO dto) {
        Scenario s = getScenarioById(id);
        s.setName(dto.getName());
        populateAssociations(s, dto);
        return scenarioRepository.save(s);
    }

    @Transactional
    public void deleteScenario(Long id) {
        if (!scenarioRepository.existsById(id)) throw new ResourceNotFoundException("Scenariu negăsit");
        scenarioRepository.deleteById(id);
    }

    private void populateAssociations(Scenario scenario, ScenarioDTO dto) {
        if (dto.getVehicleIds() != null && !dto.getVehicleIds().isEmpty()) {
            List<Vehicle> vehicles = vehicleRepository.findAllById(dto.getVehicleIds());
            scenario.setVehicles(new HashSet<>(vehicles));
        }
        if (dto.getDeliveryIds() != null && !dto.getDeliveryIds().isEmpty()) {
            List<Delivery> deliveries = deliveryRepository.findAllById(dto.getDeliveryIds());
            scenario.setDeliveries(new HashSet<>(deliveries));
        }
    }
}