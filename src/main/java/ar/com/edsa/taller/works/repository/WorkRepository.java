package ar.com.edsa.taller.works.repository;

import ar.com.edsa.taller.works.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findByDate(LocalDate date);
    List<Work> findByVehicle_LicensePlate(String licensePlate);
    List<Work> findByVehicle_LicensePlateAndDate(String licensePlate, LocalDate date);
}
