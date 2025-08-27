package ar.com.edsa.taller.works.controller;

import ar.com.edsa.taller.works.dto.WorkDTOs;
import ar.com.edsa.taller.works.service.WorkService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
public class WorkController {

    private final WorkService service;

    public WorkController(WorkService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<WorkDTOs.Response> create(@Valid @RequestBody WorkDTOs.Create in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(in));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDTOs.Response> update(@PathVariable Long id,
                                                          @Valid @RequestBody WorkDTOs.Update in) {
        return ResponseEntity.ok(service.update(id, in));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-plate/{plate}")
    public List<WorkDTOs.Response> byPlate(@PathVariable String plate) {
        return service.listByPlate(plate);
    }

    @GetMapping("/by-date")
    public List<WorkDTOs.Response> byDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.listByDate(date);
    }
}
