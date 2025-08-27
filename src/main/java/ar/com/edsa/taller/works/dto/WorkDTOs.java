package ar.com.edsa.taller.works.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class WorkDTOs {

    // Alta
    public record Create(
            @NotNull Long vehicleId,
            @NotNull LocalDate date,
            @NotBlank String title,
            String note,
            @NotNull @PositiveOrZero BigDecimal cost
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
