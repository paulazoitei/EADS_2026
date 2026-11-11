package org.example.vros.vehicle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vros.vehicle.dto.VehicleDTO;
import org.example.vros.vehicle.entity.Vehicle;
import org.example.vros.vehicle.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "CRUD API for Vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Get all vehicles (Supports JSON/XML)")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles().stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Get vehicle by ID (Supports ETag)")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(convertToDTO(vehicle));
    }

    @Operation(summary = "Create a new vehicle")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO dto) {
        Vehicle savedVehicle = vehicleService.createVehicle(dto);
        return new ResponseEntity<>(convertToDTO(savedVehicle), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a vehicle")
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO dto) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, dto);
        return ResponseEntity.ok(convertToDTO(updatedVehicle));
    }

    @Operation(summary = "Delete a vehicle")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setName(vehicle.getName());
        dto.setCapacity(vehicle.getCapacity());
        dto.setAvailable(vehicle.getAvailable());

        if (vehicle.getDepot() != null) {
            dto.setDepotId(vehicle.getDepot().getId());
        }

        return dto;
    }
}