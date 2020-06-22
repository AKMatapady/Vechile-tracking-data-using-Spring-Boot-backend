package per.akmatapady.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.akmatapady.repository.VehicleRepository;

@Service
public class TrackerService {

    @Autowired
    private VehicleRepository vehicleRepo;

}
