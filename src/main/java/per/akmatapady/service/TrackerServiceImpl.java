package per.akmatapady.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import per.akmatapady.entity.Vehicle;
import per.akmatapady.entity.VehicleReading;
import per.akmatapady.repository.ReadingRepository;
import per.akmatapady.repository.TireRepository;
import per.akmatapady.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TrackerServiceImpl implements TrackerService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ReadingRepository readingRepo;

    @Autowired
    private TireRepository tireRepo;

    @Override
    public List<Vehicle> addVehicles(List<Vehicle> vehicleList) {
//        List<Vehicle> res = new ArrayList<>();
//        for(Vehicle v : vehicleList)
//        {
//            if(!vehicleRepo.findById(v.getVin()).isPresent())
//                res.add(vehicleRepo.save(v));
//        }
       // return res;

        return (List<Vehicle>) vehicleRepo.saveAll(vehicleList);
    }

    @Override
    public VehicleReading addReadings(VehicleReading vehicleReading) {
        tireRepo.save(vehicleReading.getTires());
        return readingRepo.save(vehicleReading);
    }
}
