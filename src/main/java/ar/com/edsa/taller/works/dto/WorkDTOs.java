package ar.com.edsa.taller.works.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkDTOs {

    @Schema(description = "DTO para crear un nuevo trabajo/service", name = "WorkCreate")
    public record Create(
            @NotNull
            @Schema(description = "ID del vehículo al que se le realiza el trabajo", example = "1")
            Long vehicleId,

            @NotNull
            @Schema(description = "Fecha en que se realizó el trabajo", example = "2024-03-15")
            LocalDate date,

            @NotBlank
            @Schema(description = "Título/descripción corta del trabajo", example = "Cambio de aceite y filtro")
            String title,

            @Schema(description = "Notas adicionales sobre el trabajo", example = "Se utilizó aceite marca X")
            String note,

            @NotNull @PositiveOrZero
            @Schema(description = "Costo del trabajo", example = "25000.50")
            BigDecimal cost
    ) {}

    @Schema(description = "DTO para actualizar un trabajo/servicio existente", name = "WorkUpdate")
    public record Update(
            @NotNull
            @Schema(description = "Fecha en que se realizó el trabajo", example = "2024-03-15", requiredMode = Schema.RequiredMode.REQUIRED)
            LocalDate date,

            @NotBlank
            @Schema(description = "Título/descripción corta del trabajo", example = "Cambio de aceite y filtro", requiredMode = Schema.RequiredMode.REQUIRED)
            String title,

            @Schema(description = "Notas adicionales sobre el trabajo", example = "Se utilizó aceite sintético marca X")
            String note,

            @NotNull @PositiveOrZero
            @Schema(description = "Costo del trabajo", example = "27500.75", requiredMode = Schema.RequiredMode.REQUIRED)
            BigDecimal cost
    ) {}

    @Schema(description = "DTO de respuesta para trabajos/servicios", name = "WorkResponse")
    public record Response(
            @Schema(description = "ID del trabajo", example = "1")
            Long id,

            @Schema(description = "ID del vehículo asociado", example = "5")
            Long vehicleId,

            @Schema(description = "Patente del vehículo asociado", example = "ABC123")
            String vehiclePlate,

            @Schema(description = "Fecha en que se realizó el trabajo", example = "2024-03-15")
            LocalDate date,

            @Schema(description = "Título/descripción corta del trabajo", example = "Cambio de aceite y filtro")
            String title,

            @Schema(description = "Notas adicionales sobre el trabajo", example = "Se utilizó aceite marca X")
            String note,

            @Schema(description = "Costo del trabajo", example = "25000.50")
            BigDecimal cost
    ) {}
}
