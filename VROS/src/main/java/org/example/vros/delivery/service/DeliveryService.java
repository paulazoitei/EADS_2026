package org.example.vros.delivery.service;

import org.example.vros.common.exception.ResourceNotFoundException;
import org.example.vros.customer.entity.Customer;
import org.example.vros.customer.repository.CustomerRepository;
import org.example.vros.delivery.dto.DeliveryDTO;
import org.example.vros.delivery.entity.Delivery;
import org.example.vros.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, CustomerRepository customerRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
    }

    public List<Delivery> getAllDeliveries() { return deliveryRepository.findAll(); }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livrare negăsită: " + id));
    }

    @Transactional
    public Delivery createDelivery(DeliveryDTO dto) {
        Customer c = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Client negăsit: " + dto.getCustomerId()));
        Delivery d = new Delivery();
        d.setQuantity(dto.getQuantity());
        d.setNotes(dto.getNotes());
        d.setTimestamp(dto.getTimestamp());
        d.setCustomer(c);
        return deliveryRepository.save(d);
    }

    @Transactional
    public Delivery updateDelivery(Long id, DeliveryDTO dto) {
        Delivery d = getDeliveryById(id);
        Customer c = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Client negăsit"));
        d.setQuantity(dto.getQuantity());
        d.setNotes(dto.getNotes());
        d.setTimestamp(dto.getTimestamp());
        d.setCustomer(c);
        return deliveryRepository.save(d);
    }

    @Transactional
    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) throw new ResourceNotFoundException("Livrare negăsită");
        deliveryRepository.deleteById(id);
    }
}