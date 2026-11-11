package org.example.vros.driver.entity;

import jakarta.persistence.*;
import org.example.vros.common.entity.Person;
import org.example.vros.vehicle.entity.Vehicle;

@Entity
@Table(name = "drivers")
public class Driver extends Person {

    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}