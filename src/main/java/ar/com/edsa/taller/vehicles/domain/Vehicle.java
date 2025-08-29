package ar.com.edsa.taller.vehicles.domain;

import ar.com.edsa.taller.works.domain.Work;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "license_plate"),
                @UniqueConstraint(columnNames = "chassis_number"),
                @UniqueConstraint(columnNames = "engine_number") })
@Getter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "license_plate", nullable = false, length = 10)
    @Setter
    private String licensePlate;

    @Setter
    @Column(name = "chassis_number", nullable = false, length = 30)
    private String chassisNumber;

    @Setter
    @Column(name = "engine_number", nullable = false, length = 30)
    private String engineNumber;

    @Setter
    @Column(nullable = false, length = 50)
    private String make;

    @Setter
    @Column(nullable = false, length = 30)
    private String color;

    @Setter
    @Column(name = "manufacturing_year", nullable = false)
    private Integer manufacturingYear;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Work> works = new ArrayList<>();

    /*public void addService(Work s) {
        works.add(s);
        s.setVehicle(this);
    }
    public void removeService(Work s) {
        works.remove(s);
        s.setVehicle(null);
    }*/

    public Vehicle(String licensePlate, String chassisNumber, String engineNumber, String make, String color, Integer manufacturingYear) {
        this.licensePlate = licensePlate;
        this.chassisNumber = chassisNumber;
        this.engineNumber = engineNumber;
        this.make = make;
        this.color = color;
        this.manufacturingYear = manufacturingYear;
    }

}


