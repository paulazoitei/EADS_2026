package org.example.vros.vehicle.service;

import org.example.vros.common.exception.ResourceNotFoundException;
import org.example.vros.depot.entity.Depot;
import org.example.vros.depot.repository.DepotRepository;
import org.example.vros.vehicle.dto.VehicleDTO;
import org.example.vros.vehicle.entity.Vehicle;
import org.example.vros.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DepotRepository depotRepository;

    public VehicleService(VehicleRepository vehicleRepository, DepotRepository depotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.depotRepository = depotRepository;
    }


    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicul negăsit: " + id));
    }

    @Transactional
    public Vehicle createVehicle(VehicleDTO dto) {
        Depot depot = depotRepository.findById(dto.getDepotId())
                .orElseThrow(() -> new ResourceNotFoundException("Depozit negăsit cu id: " + dto.getDepotId()));
        Vehicle v = new Vehicle();
        v.setName(dto.getName());
        v.setCapacity(dto.getCapacity());
        v.setAvailable(dto.getAvailable());
        v.setDepot(depot);
        return vehicleRepository.save(v);
    }

    @Transactional
    public Vehicle updateVehicle(Long id, VehicleDTO dto) {
        Vehicle v = getVehicleById(id);
        Depot depot = depotRepository.findById(dto.getDepotId())
                .orElseThrow(() -> new ResourceNotFoundException("Depozit negăsit"));
        v.setName(dto.getName());
        v.setCapacity(dto.getCapacity());
        v.setAvailable(dto.getAvailable());
        v.setDepot(depot);
        return vehicleRepository.save(v);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicul negăsit");
        }
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAvailable(Integer capacity) {
        return vehicleRepository.findAvailableWithCapacity(capacity);
    }

    @Transactional
    public void setAvailability(Long id, Boolean status) {
        vehicleRepository.updateAvailability(id, status);
    }
}