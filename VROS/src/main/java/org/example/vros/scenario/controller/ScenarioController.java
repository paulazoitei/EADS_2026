package org.example.vros.scenario.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vros.delivery.entity.Delivery;
import org.example.vros.scenario.dto.ScenarioDTO;
import org.example.vros.scenario.entity.Scenario;
import org.example.vros.scenario.service.ScenarioService;
import org.example.vros.vehicle.entity.Vehicle;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scenarios")
@Tag(name = "Scenarios", description = "CRUD API for Routing Scenarios")
public class ScenarioController {

    private final ScenarioService scenarioService;

    public ScenarioController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @Operation(summary = "Get all scenarios")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<ScenarioDTO>> getAllScenarios() {
        List<ScenarioDTO> scenarios = scenarioService.getAllScenarios().stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(scenarios);
    }

    @Operation(summary = "Get scenario by ID")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<ScenarioDTO> getScenarioById(@PathVariable Long id) {
        Scenario scenario = scenarioService.getScenarioById(id);
        return ResponseEntity.ok(convertToDTO(scenario));
    }

    @Operation(summary = "Create scenario")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<ScenarioDTO> createScenario(@Valid @RequestBody ScenarioDTO dto) {
        Scenario savedScenario = scenarioService.createScenario(dto);
        return new ResponseEntity<>(convertToDTO(savedScenario), HttpStatus.CREATED);
    }

    @Operation(summary = "Update scenario")
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<ScenarioDTO> updateScenario(@PathVariable Long id, @Valid @RequestBody ScenarioDTO dto) {
        Scenario updatedScenario = scenarioService.updateScenario(id, dto);
        return ResponseEntity.ok(convertToDTO(updatedScenario));
    }

    @Operation(summary = "Delete scenario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScenario(@PathVariable Long id) {
        scenarioService.deleteScenario(id);
        return ResponseEntity.noContent().build();
    }

    private ScenarioDTO convertToDTO(Scenario scenario) {
        ScenarioDTO dto = new ScenarioDTO();
        dto.setName(scenario.getName());

        if (scenario.getVehicles() != null) {
            dto.setVehicleIds(scenario.getVehicles().stream()
                    .map(Vehicle::getId)
                    .collect(Collectors.toSet()));
        }

        if (scenario.getDeliveries() != null) {
            dto.setDeliveryIds(scenario.getDeliveries().stream()
                    .map(Delivery::getId)
                    .collect(Collectors.toSet()));
        }

        return dto;
    }
}