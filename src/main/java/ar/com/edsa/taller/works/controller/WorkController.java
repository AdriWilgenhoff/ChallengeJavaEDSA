package ar.com.edsa.taller.works.controller;

import ar.com.edsa.taller.exception.ApiError;
import ar.com.edsa.taller.works.dto.WorkDTOs;
import ar.com.edsa.taller.works.service.WorkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@Tag(name = "Services", description = "API para gestión de servicios/trabajos realizados a vehículos")
public class WorkController {

    private final WorkService service;

    public WorkController(WorkService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo servicio/trabajo",
            description = "Registra un nuevo servicio realizado a un vehículo. Requiere el ID del vehículo, fecha, descripción y costo."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Servicio creado exitosamente",
            content = @Content(schema = @Schema(implementation = WorkDTOs.Response.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Vehículo no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    public ResponseEntity<WorkDTOs.Response> create(@Valid @RequestBody WorkDTOs.Create in) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(in));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un servicio existente",
            description = "Actualiza los datos de un servicio/trabajo existente identificado por su ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Servicio actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = WorkDTOs.Response.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Servicio no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    public ResponseEntity<WorkDTOs.Response> update(@PathVariable Long id,
                                                    @Valid @RequestBody WorkDTOs.Update in) {
        return ResponseEntity.ok(service.update(id, in));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un servicio",
            description = "Elimina un servicio/trabajo existente identificado por su ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Servicio eliminado exitosamente"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Servicio no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/by-plate/{plate}")
    @Operation(
            summary = "Obtener servicios por patente de vehículo",
            description = "Retorna todos los servicios/trabajos realizados a un vehículo específico, identificado por su patente."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de servicios encontrados",
            content = @Content(schema = @Schema(implementation = WorkDTOs.Response[].class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron servicios para la patente especificada",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Parámetro de patente inválido",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    public List<WorkDTOs.Response> byPlate(@PathVariable String plate) {
        return service.listByPlate(plate);
    }

    @GetMapping("/by-date")
    @Operation(
            summary = "Obtener servicios por fecha",
            description = "Retorna todos los servicios/trabajos realizados en una fecha específica. " +
                    "Formato de fecha esperado: YYYY-MM-DD. Ejemplo: 2024-03-15"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de servicios realizados en la fecha especificada",
            content = @Content(schema = @Schema(implementation = WorkDTOs.Response[].class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Formato de fecha inválido. Use el formato YYYY-MM-DD",
            content = @Content(schema = @Schema(implementation = ApiError.class))
    )
    @ApiResponse(
            responseCode = "204",
            description = "No hay servicios registrados en la fecha especificada",
            content = @Content(schema = @Schema(implementation = Void.class))
    )
    public ResponseEntity<List<WorkDTOs.Response>> byDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<WorkDTOs.Response> services = service.listByDate(date);
        if (services.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(services);
    }
}