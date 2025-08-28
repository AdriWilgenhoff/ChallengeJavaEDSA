package ar.com.edsa.taller.works.service;

import ar.com.edsa.taller.exception.VehicleNotFoundException;
import ar.com.edsa.taller.exception.WorkNotFoundException;
import ar.com.edsa.taller.works.domain.Work;
import ar.com.edsa.taller.works.dto.WorkDTOs;
import ar.com.edsa.taller.works.mapper.WorkMapper;
import ar.com.edsa.taller.works.repository.WorkRepository;
import ar.com.edsa.taller.vehicles.domain.Vehicle;
import ar.com.edsa.taller.vehicles.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repo;
    private final VehicleRepository vehicleRepo;
    private final WorkMapper mapper;

    public WorkServiceImpl(WorkRepository repo, VehicleRepository vehicleRepo, WorkMapper mapper) {
        this.repo = repo; this.vehicleRepo = vehicleRepo; this.mapper = mapper;
    }

    @Override @Transactional
    public WorkDTOs.Response create(WorkDTOs.Create in) {
        Vehicle v = vehicleRepo.findById(in.vehicleId())
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        Work s = mapper.toEntity(in, v);
        return mapper.toResponse(repo.save(s));
    }

    @Override @Transactional
    public WorkDTOs.Response update(Long id, WorkDTOs.Update in) {
        Work s = repo.findById(id)
                .orElseThrow(() -> new WorkNotFoundException("Service job not found"));
        mapper.update(s, in);
        return mapper.toResponse(repo.save(s));
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new WorkNotFoundException("Service job not found");
        repo.deleteById(id);
    }

    @Override
    public List<WorkDTOs.Response> listByPlate(String plate) {
        return repo.findByVehicle_LicensePlate(plate).stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<WorkDTOs.Response> listByDate(LocalDate date) {
        return repo.findByDate(date).stream().map(mapper::toResponse).toList();
    }
}
