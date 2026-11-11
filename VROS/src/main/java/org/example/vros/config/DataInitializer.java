package org.example.vros.config;

import org.example.vros.customer.entity.Customer;
import org.example.vros.customer.repository.CustomerRepository;
import org.example.vros.depot.entity.Depot;
import org.example.vros.depot.repository.DepotRepository;
import org.example.vros.vehicle.entity.Vehicle;
import org.example.vros.vehicle.repository.VehicleRepository;
import org.example.vros.vehicle.service.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner testRepositoriesAndServices(
            CustomerRepository customerRepository,
            DepotRepository depotRepository,
            VehicleRepository vehicleRepository,
            VehicleService vehicleService
    ) {
        return args -> {



            Customer customer = customerRepository
                    .findByEmail("ion.popescu@example.com")
                    .orElseGet(() -> {
                        Customer newCustomer = new Customer(
                                "Ion Popescu",
                                "ion.popescu@example.com",
                                "0712345678",
                                "Strada Libertatii nr. 10, Bucuresti",
                                "44.4268, 26.1025"
                        );
                        return customerRepository.save(newCustomer);
                    });

            System.out.println("Customer salvat:");
            System.out.println(customer);

            System.out.println("Numar total clienti: " + customerRepository.count());

            System.out.println("Toti clientii:");
            customerRepository.findAll().forEach(System.out::println);





            Depot depot = depotRepository.findByName("Depozit Ilfov").orElseGet(() -> {
                Depot newDepot = new Depot();
                newDepot.setName("Depozit Ilfov");
                newDepot.setCoordinates("44.5000, 26.2000");
                return depotRepository.save(newDepot);
            });


            Vehicle vehicle;
            List<Vehicle> masiniExistente = vehicleRepository.findAll();
            if (masiniExistente.isEmpty()) {
                Vehicle newVehicle = new Vehicle();
                newVehicle.setName("Ford Transit");
                newVehicle.setCapacity(500);
                newVehicle.setAvailable(true);
                newVehicle.setDepot(depot);
                vehicle = vehicleRepository.save(newVehicle);
                System.out.println("S-a salvat vehiculul: " + vehicle.getName() + " cu ID-ul: " + vehicle.getId());
            } else {
                vehicle = masiniExistente.get(0);
                System.out.println("Vehiculul exista deja în baza de date: " + vehicle.getName());
            }




            List<Vehicle> masiniMari = vehicleService.getAvailable(400);
            System.out.println("Număr vehicule disponibile cu capacitate minimă 400: " + masiniMari.size());


            System.out.println("Setăm vehiculul cu ID-ul " + vehicle.getId() + " ca fiind indisponibil...");

            vehicleService.setAvailability(vehicle.getId(), false);


            Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).orElseThrow();
            System.out.println("Starea nouă de disponibilitate a vehiculului: " + updatedVehicle.getAvailable());


        };
    }
}