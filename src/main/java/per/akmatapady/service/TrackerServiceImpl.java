package per.akmatapady.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import per.akmatapady.entity.*;
import per.akmatapady.exception.ResourceNotFoundException;
import per.akmatapady.repository.AlertRepository;
import per.akmatapady.repository.ReadingRepository;
import per.akmatapady.repository.TireRepository;
import per.akmatapady.repository.VehicleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TrackerServiceImpl implements TrackerService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ReadingRepository readingRepo;

    @Autowired
    private TireRepository tireRepo;

    @Autowired
    private AlertRepository alertRepo;

    @Override
    @Transactional
    public List<Vehicle> addVehicles(List<Vehicle> vehicleList) {
        return (List<Vehicle>) vehicleRepo.saveAll(vehicleList);
    }

    @Override
    @Transactional
    public VehicleReading addReadings(VehicleReading vehicleReading) {
        Optional<Vehicle> v = vehicleRepo.findById(vehicleReading.getVin());
        if(!v.isPresent())
        {
            System.out.println(vehicleReading.getVin() + " not present in vehicle table");
            throw new ResourceNotFoundException("Vehicle with ID: " + vehicleReading.getVin() + " not found");
        }
        tireRepo.save(vehicleReading.getTires());
        if(vehicleReading.getEngineRpm() > v.get().getRedlineRpm())
        {
            Alert alert = new Alert();
            alert.setVin(vehicleReading.getVin());
            alert.setTimestamp(vehicleReading.getTimestamp());
            alert.setAlertRule(AlertRule.RPM_TOO_HIGH);
            alert.setPriority(Severity.HIGH);
            alertRepo.save(alert);
        }
        if(vehicleReading.getFuelVolume() < (0.1 * v.get().getMaxFuelVolume()))
        {
            Alert alert = new Alert();
            alert.setVin(v.get().getVin());
            alert.setTimestamp(vehicleReading.getTimestamp());
            alert.setAlertRule(AlertRule.LOW_FUEL);
            alert.setPriority(Severity.MEDIUM);
            alertRepo.save(alert);
        }
        if(vehicleReading.getTires().getMinTirePressure() < 32 || vehicleReading.getTires().getMaxTirePressure() > 36)
        {
            Alert alert = new Alert();
            alert.setVin(v.get().getVin());
            alert.setTimestamp(vehicleReading.getTimestamp());
            alert.setAlertRule(AlertRule.INCORRECT_TIRE_PRESSURE);
            alert.setPriority(Severity.LOW);
            alertRepo.save(alert);
        }
        if(vehicleReading.isCheckEngineLightOn() || vehicleReading.isEngineCoolantLow())
        {
            Alert alert = new Alert();
            alert.setVin(v.get().getVin());
            alert.setTimestamp(vehicleReading.getTimestamp());
            alert.setAlertRule(AlertRule.ENGINE_CHECK_REQUIRED);
            alert.setPriority(Severity.LOW);
            alertRepo.save(alert);
        }
        return readingRepo.save(vehicleReading);
    }

    @Override
    @Transactional
    public Vehicle findOneVehicle(String vin) {
        Optional<Vehicle> v = vehicleRepo.findById(vin);
        if(!v.isPresent())
        {
            throw new ResourceNotFoundException("employee with ID: " + vin + " not found");
        }
        return v.get();
    }

    @Override
    @Transactional
    public List<Vehicle> findAllVehicles() {
        return (List<Vehicle>) vehicleRepo.findAll();
    }

    @Override
    @Transactional
    public List<Alert> findAllAlerts(String vin) {
        Optional<Vehicle> v = vehicleRepo.findById(vin);
        if(!v.isPresent())
        {
            throw new ResourceNotFoundException("employee with ID: " + vin + " not found");
        }
        return alertRepo.findByVin(vin);
    }

    @Override
    @Transactional
    public List<Alert> findHighPriority(){
        List<Alert> res = alertRepo.findByPriority(Severity.HIGH);
        List<Alert> res2 = new ArrayList<>();
        int len = res.size();
//        SimpleDateFormat format = new SimpleDateFormat(
//                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
//        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Instant now = Instant.now();
        for(int i = 0; i < len; i++)
        {
//            Instant t = Instant.parse(res.get(i).getTimestamp());
//            Duration d = Duration.between(t, now);
            try
            {
                System.out.println("Priority diff: " + ChronoUnit.HOURS.between(Instant.parse(res.get(i).getTimestamp()), now));
                if(Math.abs(ChronoUnit.HOURS.between(Instant.parse(res.get(i).getTimestamp()), now)) <= 2)
                {
                    res2.add(res.get(i));
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        return res2;
    }

    @Override
    @Transactional
    public List<Location> findLocation(String vin) {
        Optional<Vehicle> v = vehicleRepo.findById(vin);
        if(!v.isPresent())
        {
            throw new ResourceNotFoundException("employee with ID: " + vin + " not found");
        }
        List<VehicleReading> res = readingRepo.findByVin(vin);
        List<Location> ans = new ArrayList<>();
        Instant now = Instant.now();
        for(int i = 0; i < res.size(); i++)
        {
            try
            {
                System.out.println("Location diff: " + ChronoUnit.MINUTES.between(Instant.parse(res.get(i).getTimestamp()), now));
                if(Math.abs(ChronoUnit.MINUTES.between(Instant.parse(res.get(i).getTimestamp()), now)) <= 30)
                {
                    Location loc = new Location();
                    loc.setLatitude(res.get(i).getLatitude());
                    loc.setLongitude(res.get(i).getLongitude());
                    ans.add(loc);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        return ans;
    }
}
