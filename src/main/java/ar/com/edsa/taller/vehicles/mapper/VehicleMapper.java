package ar.com.edsa.taller.vehicles.mapper;

import ar.com.edsa.taller.vehicles.domain.Vehicle;
import ar.com.edsa.taller.vehicles.dto.VehicleDTOs;
import ar.com.edsa.taller.works.dto.WorkDTOs;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class VehicleMapper {

    /** Create DTO -> Entity */
    public Vehicle toEntity(VehicleDTOs.Create in) {
        Vehicle v = new Vehicle();
        v.setLicensePlate(normalize(in.licensePlate()));
        v.setChassisNumber(normalize(in.chassisNumber()));
        v.setEngineNumber(normalize(in.engineNumber()));
        v.setMake(in.make());
        v.setColor(in.color());
        v.setManufacturingYear(in.manufacturingYear());
        return v;
    }

    /** Update DTO -> aplica cambios sobre la Entity existente */
    public void update(Vehicle v, VehicleDTOs.Update in) {
        v.setMake(in.make());
        v.setColor(in.color());
        v.setManufacturingYear(in.manufacturingYear());
    }

    /** Entity -> Response DTO */
    public VehicleDTOs.Response toResponse(Vehicle v) {
        return new VehicleDTOs.Response(
                v.getId(),
                v.getLicensePlate(),
                v.getChassisNumber(),
                v.getEngineNumber(),
                v.getMake(),
                v.getColor(),
                v.getManufacturingYear()
        );
    }

    public VehicleDTOs.ResponseWithWorks toResponseWithWorks(Vehicle v, List<WorkDTOs.Response> work) {
        return new VehicleDTOs.ResponseWithWorks(
                v.getId(), v.getLicensePlate(), v.getChassisNumber(), v.getEngineNumber(),
                v.getMake(), v.getColor(), v.getManufacturingYear(), work
        );
    }


    private static String normalize(String s) {
        return s == null ? null : s.trim().toUpperCase(Locale.ROOT);
    }
}
