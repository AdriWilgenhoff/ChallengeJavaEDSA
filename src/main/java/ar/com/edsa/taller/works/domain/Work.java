package ar.com.edsa.taller.works.domain;

import ar.com.edsa.taller.vehicles.domain.Vehicle;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "service_job")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_job_seq")
    @SequenceGenerator(name = "service_job_seq", sequenceName = "service_job_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(length = 500)
    private String note;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal cost;
}