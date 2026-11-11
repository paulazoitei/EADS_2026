package org.example.vros.delivery.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class DeliveryDTO {
    @NotNull(message = "Cantitatea este obligatorie")
    @Min(value = 1, message = "Cantitatea minimă este 1")
    private Integer quantity;

    private String notes;

    @NotNull(message = "Timestamp-ul este obligatoriu")
    private LocalDateTime timestamp;

    @NotNull(message = "ID-ul clientului este obligatoriu")
    private Long customerId;

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}