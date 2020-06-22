package per.akmatapady.repository;

import org.springframework.data.repository.CrudRepository;
import per.akmatapady.entity.VehicleTires;

public interface TireRepository extends CrudRepository<VehicleTires, String> {
}
