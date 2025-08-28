package ar.com.edsa.taller.works.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkDTOs {

    @Schema(description = "DTO para crear un nuevo trabajo/service")
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

    // Edición
    public record Update(
            @NotNull LocalDate date,
            @NotBlank String title,
            String note,
            @NotNull @PositiveOrZero BigDecimal cost
    ) {}

    // Salida
    public record Response(
            Long id, Long vehicleId, String vehiclePlate,
            LocalDate date, String title, String note, BigDecimal cost
    ) {}
}
