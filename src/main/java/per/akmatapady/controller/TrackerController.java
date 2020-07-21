package per.akmatapady.controller;

import com.amazonaws.services.sns.AmazonSNS;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import per.akmatapady.awsmessaging.AlertNotification;
import per.akmatapady.entity.Alert;
import per.akmatapady.entity.Location;
import per.akmatapady.entity.Vehicle;
import per.akmatapady.entity.VehicleReading;
import per.akmatapady.service.TrackerService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class TrackerController {

    @Autowired
    @Qualifier("trackerServiceImpl")
    private TrackerService trackerService;


    @PutMapping(value = "vehicles")
    public List<Vehicle> addVehicles(@RequestBody List<Vehicle> vehicleList)
    {
        return trackerService.addVehicles(vehicleList);
    }

    @PostMapping(value = "readings")
    public VehicleReading addReading(@RequestBody VehicleReading vehicleReading) throws JsonProcessingException {
        return trackerService.addReadings(vehicleReading);
    }

    @GetMapping(value = "vehicles")
    public List<Vehicle> findAllVehicles()
    {
        return trackerService.findAllVehicles();
    }

    @GetMapping(value = "alert/{id}")
    public List<Alert> findAllAlerts(@PathVariable("id") String vin)
    {
        return trackerService.findAllAlerts(vin);
    }

    @GetMapping(value = "alert/high")
    public List<Alert> findHighAlert(){
        return trackerService.findHighPriority();
    }

    @GetMapping(value = "location/{id}")
    public List<Location> findLocation(@PathVariable("id") String vin)
    {
        return trackerService.findLocation(vin);
    }

}
