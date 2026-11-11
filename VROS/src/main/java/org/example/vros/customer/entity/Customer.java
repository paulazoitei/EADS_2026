package org.example.vros.customer.entity;

import jakarta.persistence.*;
import org.example.vros.common.entity.Person;
import org.example.vros.delivery.entity.Delivery;

import java.util.List;

@Entity
@Table(
        name = "customers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_customers_email",
                        columnNames = "email"
                )
        }
)
public class Customer extends Person {

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 255)
    private String coordinates;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Delivery> deliveries;

    public Customer() {
        super();
    }

    public Customer(String name, String email, String phone, String address, String coordinates) {
        super(name, email, phone);
        this.address = address;
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", address='" + address + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}