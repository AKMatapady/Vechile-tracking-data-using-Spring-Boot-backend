package per.akmatapady.service;

import per.akmatapady.entity.Vehicle;
import per.akmatapady.entity.VehicleReading;

import java.util.*;

public interface TrackerService {
    List<Vehicle> addVehicles(List<Vehicle> vehicleList);
    VehicleReading addReadings(VehicleReading vehicleReading);
}
