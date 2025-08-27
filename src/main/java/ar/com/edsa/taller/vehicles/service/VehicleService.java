package ar.com.edsa.taller.vehicles.service;

import ar.com.edsa.taller.vehicles.dto.VehicleDTOs;

import java.time.LocalDate;
import java.util.List;

public interface VehicleService {

    VehicleDTOs.Response create(VehicleDTOs.Create in);

    VehicleDTOs.Response update(Long id, VehicleDTOs.Update in);

    void delete(Long id);

    VehicleDTOs.Response findById(Long id);

    VehicleDTOs.Response findByLicensePlate(String plate);

    List<VehicleDTOs.Response> findAll();

    List<VehicleDTOs.ResponseWithWorks> findAttendedOn(LocalDate date);

    VehicleDTOs.ResponseWithWorks findByPlateWithServices(String plate);

}
