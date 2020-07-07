package per.akmatapady.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import per.akmatapady.entity.Alert;
import per.akmatapady.entity.Severity;

import java.util.List;
import java.util.Optional;

public interface AlertRepository extends CrudRepository<Alert, String> {
    List<Alert> findByVin(String vin);
    List<Alert> findByPriority(Severity value);
}
