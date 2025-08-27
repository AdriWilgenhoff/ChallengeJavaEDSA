package ar.com.edsa.taller.works.service;

import ar.com.edsa.taller.works.dto.WorkDTOs;

import java.time.LocalDate;
import java.util.List;

public interface WorkService {
    WorkDTOs.Response create(WorkDTOs.Create in);
    WorkDTOs.Response update(Long id, WorkDTOs.Update in);
    void delete(Long id);

    List<WorkDTOs.Response> listByPlate(String plate);
    List<WorkDTOs.Response> listByDate(LocalDate date);
}
