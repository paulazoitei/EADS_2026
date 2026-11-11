package org.example.vros.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.vros.delivery.dto.DeliveryDTO;
import org.example.vros.delivery.entity.Delivery;
import org.example.vros.delivery.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@Tag(name = "Deliveries", description = "CRUD API for Deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Get all deliveries")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries().stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(deliveries);
    }

    @Operation(summary = "Get delivery by ID")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(convertToDTO(delivery));
    }

    @Operation(summary = "Create delivery")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<DeliveryDTO> createDelivery(@Valid @RequestBody DeliveryDTO dto) {
        Delivery savedDelivery = deliveryService.createDelivery(dto);
        return new ResponseEntity<>(convertToDTO(savedDelivery), HttpStatus.CREATED);
    }

    @Operation(summary = "Update delivery")
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryDTO dto) {
        Delivery updatedDelivery = deliveryService.updateDelivery(id, dto);
        return ResponseEntity.ok(convertToDTO(updatedDelivery));
    }

    @Operation(summary = "Delete delivery")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

    private DeliveryDTO convertToDTO(Delivery delivery) {
        DeliveryDTO dto = new DeliveryDTO();
        dto.setQuantity(delivery.getQuantity());
        dto.setNotes(delivery.getNotes());
        dto.setTimestamp(delivery.getTimestamp());

        if (delivery.getCustomer() != null) {
            dto.setCustomerId(delivery.getCustomer().getId());
        }

        return dto;
    }
}