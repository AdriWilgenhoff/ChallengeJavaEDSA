package ar.com.edsa.taller.vehicles.service;

import ar.com.edsa.taller.exception.VehicleNotFoundException;
import ar.com.edsa.taller.vehicles.domain.Vehicle;
import ar.com.edsa.taller.vehicles.dto.VehicleDTOs;
import ar.com.edsa.taller.vehicles.mapper.VehicleMapper;
import ar.com.edsa.taller.vehicles.repository.VehicleRepository;
import ar.com.edsa.taller.works.dto.WorkDTOs;
import ar.com.edsa.taller.works.mapper.WorkMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repo;
    private final VehicleMapper mapper;
    private final WorkMapper workMapper;

    public VehicleServiceImpl(VehicleRepository repo, VehicleMapper mapper, WorkMapper workMapper) {
        this.repo = repo;
        this.mapper = mapper;
        this.workMapper = workMapper;
    }

    @Override
    @Transactional
    public VehicleDTOs.Response create(VehicleDTOs.Create in) {
        if (repo.existsByLicensePlate(in.licensePlate())) {
            throw new IllegalArgumentException("Patente existente");
        }
        if (repo.existsByChassisNumber(in.chassisNumber())) {
            throw new IllegalArgumentException("Numero de Chasis existente");
        }
        if (repo.existsByEngineNumber(in.engineNumber())) {
            throw new IllegalArgumentException("Numero de Motor existente");
        }

        Vehicle v = mapper.toEntity(in);
        v = repo.save(v);
        return mapper.toResponse(v);
    }

    @Override
    @Transactional
    public VehicleDTOs.Response update(Long id, VehicleDTOs.Update in) {
        Vehicle v = repo.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehículo no encontrado con ID: " + id));

        mapper.update(v, in);
        v = repo.save(v);
        return mapper.toResponse(v);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new VehicleNotFoundException("Vehículo no encontrado con ID: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public VehicleDTOs.Response findById(Long id) {
        Vehicle v = repo.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehículo no encontrado con ID: " + id));
        return mapper.toResponse(v);
    }

    @Override
    public VehicleDTOs.Response findByLicensePlate(String plate) {
        Vehicle v = repo.findByLicensePlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException("Vehículo no encontrado con patente: " + plate));
        return mapper.toResponse(v);
    }

    @Override
    public List<VehicleDTOs.Response> findAll() {
        List<Vehicle> vehicles = repo.findAll();
        List<VehicleDTOs.Response> responses = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            VehicleDTOs.Response response = mapper.toResponse(vehicle);
            responses.add(response);
        }
        return responses;
    }

    public VehicleDTOs.ResponseWithWorks findByPlateWithServices(String plate) {
        Vehicle v = repo.findWithServicesByLicensePlate(plate)
                .orElseThrow(() ->new VehicleNotFoundException("Vehículo no encontrado con patente: " + plate));
        List<WorkDTOs.Response> works = v.getWorks().stream().map(workMapper::toResponse).toList();
        return mapper.toResponseWithWorks(v, works);
    }

    public List<VehicleDTOs.ResponseWithWorks> findAttendedOn(LocalDate date) {
        List<Vehicle> vehicles = repo.findByWorksDate(date);
        return vehicles.stream()
                .map(v -> mapper.toResponseWithWorks(
                        v, v.getWorks().stream().map(workMapper::toResponse).toList()))
                .toList();
    }

}
