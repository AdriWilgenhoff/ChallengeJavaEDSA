package ar.com.edsa.taller.vehicles.dto;

import ar.com.edsa.taller.works.dto.WorkDTOs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;

public class VehicleDTOs {

    /** DTO para crear vehículo */
    public record Create(
            @NotBlank
            @Pattern(regexp = "([A-Z]{3}\\d{3}|[A-Z]{2}\\d{2}[A-Z]{2})",
            message = "Formato de patente inválido (XXX000 o XX00XX)")
            String licensePlate,

            @NotBlank String chassisNumber,
            @NotBlank String engineNumber,

            @NotBlank String make,
            @NotBlank String color,

            @NotNull
            @Min(value = 1900, message = "El año debe ser igual o mayor a 1900")
            @Max(value = 2100, message = "El año no puede ser mayor a 2100")
            Integer manufacturingYear
        ) {
                public Create {
                        if (licensePlate != null) {
                                licensePlate = licensePlate.toUpperCase();
                        }
                }
        }

    /** DTO para actualizar (solo campos editables) */
    public record Update(
            @NotBlank String make,
            @NotBlank String color,
            @NotNull @Min(1900) @Max(2100) Integer manufacturingYear
    ) {}

    /** DTO de salida (lo que retorna la API) */
    @Schema(description = "DTO de respuesta para vehículos")
    public record Response(
            @Schema(description = "ID del vehículo", example = "1")
            Long id,

            @Schema(description = "Patente del vehículo", example = "ABC123")
            String licensePlate,

            @Schema(description = "Número de chasis", example = "CH123456789")
            String chassisNumber,

            @Schema(description = "Número de motor", example = "EN123456789")
            String engineNumber,

            @Schema(description = "Marca del vehículo", example = "Toyota")
            String make,

            @Schema(description = "Color del vehículo", example = "Rojo")
            String color,

            @Schema(description = "Año de fabricación", example = "2023")
            Integer manufacturingYear
    ) {}

    public record ResponseWithWorks(
            Long id, String licensePlate, String chassisNumber, String engineNumber,
            String brand, String color, Integer manufacturingYear,
            List<WorkDTOs.Response> services
    ) {}
}
