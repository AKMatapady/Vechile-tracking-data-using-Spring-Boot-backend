package per.akmatapady.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.akmatapady.entity.Vehicle;
import per.akmatapady.entity.VehicleReading;
import per.akmatapady.service.TrackerService;

import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @PutMapping(value = "vehicles")
    private List<Vehicle> addVehicles(@RequestBody List<Vehicle> vehicleList)
    {
        return trackerService.addVehicles(vehicleList);
    }

    @PostMapping(value = "readings")
    private VehicleReading addReading(@RequestBody VehicleReading vehicleReading)
    {
        return trackerService.addReadings(vehicleReading);
    }

}
