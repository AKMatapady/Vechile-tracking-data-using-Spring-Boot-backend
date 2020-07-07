package per.akmatapady.service;

import per.akmatapady.entity.*;

import java.text.ParseException;
import java.util.*;

public interface TrackerService {
    List<Vehicle> addVehicles(List<Vehicle> vehicleList);
    VehicleReading addReadings(VehicleReading vehicleReading);
    Vehicle findOneVehicle(String vin);
    List<Vehicle> findAllVehicles();
    List<Alert> findAllAlerts(String vin);
    List<Alert> findHighPriority();
    List<Location> findLocation(String vin);
}
