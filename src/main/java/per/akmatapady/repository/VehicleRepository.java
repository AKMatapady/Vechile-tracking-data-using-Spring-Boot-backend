package per.akmatapady.repository;

import org.springframework.data.repository.CrudRepository;
import per.akmatapady.entity.Vehicle;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {
}
