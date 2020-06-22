package per.akmatapady.repository;

import org.springframework.data.repository.CrudRepository;
import per.akmatapady.entity.VehicleReading;
import per.akmatapady.entity.VehicleReadingID;

public interface ReadingRepository extends CrudRepository<VehicleReading, VehicleReadingID> {
}
