package org.example.vros.scenario.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class ScenarioDTO {
    @NotBlank(message = "Numele scenariului este obligatoriu")
    private String name;

    private Set<Long> vehicleIds;
    private Set<Long> deliveryIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Long> getVehicleIds() { return vehicleIds; }
    public void setVehicleIds(Set<Long> vehicleIds) { this.vehicleIds = vehicleIds; }
    public Set<Long> getDeliveryIds() { return deliveryIds; }
    public void setDeliveryIds(Set<Long> deliveryIds) { this.deliveryIds = deliveryIds; }
}