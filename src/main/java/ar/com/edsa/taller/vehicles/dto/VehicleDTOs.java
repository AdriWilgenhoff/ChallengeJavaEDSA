package ar.com.edsa.taller.vehicles.dto;

import ar.com.edsa.taller.works.dto.WorkDTOs;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;

public class VehicleDTOs {

    @Schema(description = "DTO para crear un nuevo vehículo")
    public record Create(
            @Schema(description = "Patente del vehículo", example = "ABC123", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Pattern(regexp = "([A-Z]{3}\\d{3}|[A-Z]{2}\\d{2}[A-Z]{2})",
                    message = "Formato de patente inválido (XXX000 o XX00XX)")
            String licensePlate,

            @Schema(description = "Número de chasis del vehículo", example = "CH123456789", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String chassisNumber,

            @Schema(description = "Número de motor del vehículo", example = "EN123456789", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String engineNumber,

            @Schema(description = "Marca del vehículo", example = "Toyota", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String make,

            @Schema(description = "Color del vehículo", example = "Rojo", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String color,

            @Schema(description = "Año de fabricación del vehículo", example = "2023", requiredMode = Schema.RequiredMode.REQUIRED)
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

    @Schema(description = "DTO para actualizar información de un vehículo existente")
    public record Update(
            @Schema(description = "Marca del vehículo", example = "Toyota", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String make,

            @Schema(description = "Color del vehículo", example = "Azul", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank String color,

            @Schema(description = "Año de fabricación del vehículo", example = "2022", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull @Min(1900) @Max(2100) Integer manufacturingYear
    ) {}

    @Schema(description = "DTO de respuesta para vehículos")
    public record Response(
            @Schema(description = "ID del vehículo", example = "1")
            Long id,

            @Schema(description = "Patente del vehículo", example = "ABC123 o AA123FF")
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

    @Schema(description = "DTO de respuesta para vehículos que incluye información de servicios realizados")
    public record ResponseWithWorks(
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
            Integer manufacturingYear,

            @Schema(description = "Lista de servicios realizados en el vehículo")
            List<WorkDTOs.Response> services
    ) {}
}