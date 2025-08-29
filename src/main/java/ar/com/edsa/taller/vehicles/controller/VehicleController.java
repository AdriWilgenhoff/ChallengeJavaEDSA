package ar.com.edsa.taller.vehicles.controller;

import ar.com.edsa.taller.exception.ApiError;
import ar.com.edsa.taller.vehicles.dto.VehicleDTOs;
import ar.com.edsa.taller.vehicles.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@Validated
@Tag(name = "Vehicles", description = "API para gestión de vehículos")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo vehículo")
    @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente",
            content = @Content(schema = @Schema(implementation = VehicleDTOs.Create.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros inválidos",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<VehicleDTOs.Response> create(@Valid @RequestBody VehicleDTOs.Create in) {
        var out = service.create(in);
        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un vehículo existente")
    @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = VehicleDTOs.Update.class)))
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    public ResponseEntity<VehicleDTOs.Response> update(@PathVariable Long id,
                                                       @Valid @RequestBody VehicleDTOs.Update in) {
        return ResponseEntity.ok(service.update(id, in));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vehículo")
    @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/{plate}")
    @Operation(summary = "Buscar vehículo por patente")
    @ApiResponse(responseCode = "200", description = "Vehículo encontrado", content = @Content(schema = @Schema(implementation = VehicleDTOs.Response.class)))
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<VehicleDTOs.Response> findByPlate(@PathVariable String plate) {
        return ResponseEntity.ok(service.findByLicensePlate(plate));
    }

    @GetMapping
    @Operation(summary = "Obtener todos los vehículos")
    @ApiResponse(responseCode = "200",  content = @Content(schema = @Schema(implementation = VehicleDTOs.Response.class)))
    public ResponseEntity<List<VehicleDTOs.Response>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{plate}/with-works")
    @Operation(summary = "Obtener vehículo con sus prestaciones por patente", description = "Obtiene un vehículo completo incluyendo todas sus prestaciones (trabajos) asociadas, utilizando la patente como criterio de búsqueda.")
    @ApiResponse(responseCode = "200", description = "Vehículo con prestaciones encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = VehicleDTOs.ResponseWithWorks.class)))
    @ApiResponse(responseCode = "404", description = "Vehículo no encontrado con la patente especificada",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "400", description = "Parámetro de patente inválido",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public ResponseEntity<VehicleDTOs.ResponseWithWorks> findByPlateWithServices(@PathVariable String plate) {
        return ResponseEntity.ok(service.findByPlateWithServices(plate));
    }

    @GetMapping("/attended")
    @Operation(summary = "Obtener vehículos atendidos en una fecha específica",description = "Retorna todos los vehículos que recibieron prestaciones en una fecha particular, incluyendo las prestaciones realizadas en esa fecha. Formato de fecha esperado: YYYY-MM-DD. Ejemplo: 2024-03-15")
    @ApiResponse(responseCode = "200", description = "Lista de vehículos atendidos en la fecha especificada",
            content = @Content(schema = @Schema(implementation = VehicleDTOs.ResponseWithWorks[].class)))
    @ApiResponse(responseCode = "400", description = "Formato de fecha inválido (use el formato YYYY-MM-DD) o parámetros incorrectos",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "204", description = "No hay vehículos atendidos en la fecha especificada",
            content = @Content(schema = @Schema(implementation = Void.class)))
    public ResponseEntity<List<VehicleDTOs.ResponseWithWorks>> findAttendedOn(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.findAttendedOn(date));
    }


}
