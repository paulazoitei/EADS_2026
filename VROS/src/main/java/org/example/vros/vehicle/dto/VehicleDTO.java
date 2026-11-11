package org.example.vros.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleDTO {
    @NotBlank(message = "Numele este obligatoriu")
    private String name;

    @NotNull(message = "Capacitatea este obligatorie")
    @Min(value = 1, message = "Capacitatea minimă este 1")
    private Integer capacity;

    @NotNull(message = "Disponibilitatea este obligatorie")
    private Boolean available;

    @NotNull(message = "ID-ul depozitului este obligatoriu")
    private Long depotId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    public Long getDepotId() { return depotId; }
    public void setDepotId(Long depotId) { this.depotId = depotId; }
}