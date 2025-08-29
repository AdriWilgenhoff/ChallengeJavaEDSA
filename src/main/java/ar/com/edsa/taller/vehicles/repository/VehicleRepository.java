package ar.com.edsa.taller.vehicles.repository;

import ar.com.edsa.taller.vehicles.domain.Vehicle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Existencias (para validar unicidad antes del insert/update)
    boolean existsByLicensePlate(String licensePlate);
    boolean existsByChassisNumber(String chassisNumber);
    boolean existsByEngineNumber(String engineNumber);

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    @EntityGraph(attributePaths = "works")
    Optional<Vehicle> findWithServicesByLicensePlate(String licensePlate);

    List<Vehicle> findByWorksDate(LocalDate date);
}
