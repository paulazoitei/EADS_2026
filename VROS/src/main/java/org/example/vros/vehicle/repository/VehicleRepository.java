package org.example.vros.vehicle.repository;

import org.example.vros.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {


    @Query("SELECT v FROM Vehicle v WHERE v.available = true AND v.capacity >= :minCapacity")
    List<Vehicle> findAvailableWithCapacity(@Param("minCapacity") Integer minCapacity);


    @Modifying
    @Query("UPDATE Vehicle v SET v.available = :status WHERE v.id = :id")
    int updateAvailability(@Param("id") Long id, @Param("status") Boolean status);
}