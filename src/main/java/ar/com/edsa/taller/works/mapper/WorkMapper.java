package ar.com.edsa.taller.works.mapper;

import ar.com.edsa.taller.works.domain.Work;
import ar.com.edsa.taller.works.dto.WorkDTOs;
import ar.com.edsa.taller.vehicles.domain.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class WorkMapper {

    public Work toEntity(WorkDTOs.Create in, Vehicle vehicle) {
        return Work.builder()
                .vehicle(vehicle)
                .date(in.date())
                .title(in.title())
                .note(in.note())
                .cost(in.cost())
                .build();
    }

    public void update(Work entity, WorkDTOs.Update in) {
        entity.setDate(in.date());
        entity.setTitle(in.title());
        entity.setNote(in.note());
        entity.setCost(in.cost());
    }

    public WorkDTOs.Response toResponse(Work s) {
        return new WorkDTOs.Response(
                s.getId(),
                s.getVehicle() != null ? s.getVehicle().getId() : null,
                s.getVehicle() != null ? s.getVehicle().getLicensePlate() : null,
                s.getDate(), s.getTitle(), s.getNote(), s.getCost()
        );
    }
}

