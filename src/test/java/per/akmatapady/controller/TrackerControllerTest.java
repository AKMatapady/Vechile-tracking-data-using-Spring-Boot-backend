package per.akmatapady.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import per.akmatapady.entity.Vehicle;
import per.akmatapady.entity.VehicleReading;
import per.akmatapady.entity.VehicleTires;
import per.akmatapady.repository.AlertRepository;
import per.akmatapady.repository.ReadingRepository;
import per.akmatapady.repository.TireRepository;
import per.akmatapady.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@RunWith(JUnitPlatform.class)
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("integrationTest")
class TrackerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ReadingRepository readingRepo;

    @Autowired
    private TireRepository tireRepo;

    @Autowired
    private AlertRepository alertRepo;

    @BeforeEach
    void setUp() {
        Instant now;
        Vehicle v1 = new Vehicle();
        v1.setVin("Vin1");
        v1.setMake("make1");
        v1.setYear(2020);
        v1.setRedlineRpm(500);
        v1.setModel("m1");
        v1.setMaxFuelVolume(50);
        now = Instant.now();
        v1.setLastServiceDate(now.toString());
        vehicleRepo.save(v1);
        Vehicle v2 = new Vehicle();
        v2.setVin("Vin2");
        v2.setMake("make2");
        v2.setYear(2002);
        v2.setRedlineRpm(502);
        v2.setModel("m2");
        v2.setMaxFuelVolume(52);
        now = Instant.now();
        v2.setLastServiceDate(now.toString());
        vehicleRepo.save(v2);
        VehicleReading r1 = new VehicleReading();
        r1.setVin("Vin1");
        r1.setCheckEngineLightOn(true);
        VehicleTires t1 = new VehicleTires(32,33,34,32);
        tireRepo.save(t1);
        r1.setTires(t1);
        now = Instant.now();
        r1.setTimestamp(now.toString());
        r1.setSpeed(56);
        r1.setLongitude((float) 32.765);
        r1.setLatitude((float) 76.43242332);
        r1.setFuelVolume(3);
        r1.setEngineRpm(2000);
        r1.setEngineHp(12);
        r1.setEngineCoolantLow(false);
        r1.setCruiseControlOn(false);
        VehicleReading r2 = new VehicleReading();
        r2.setVin("Vin1");
        r2.setCheckEngineLightOn(true);
        VehicleTires t2 = new VehicleTires(32,33,34,32);
        tireRepo.save(t2);
        r2.setTires(t2);
        //r2.setTimestamp("2020-06-30T21:05:25.268Z");
        now = Instant.now();
        r2.setTimestamp(now.toString());
        r2.setSpeed(56);
        r2.setLongitude((float) 54.3425);
        r2.setLatitude((float) 45.8743);
        r2.setFuelVolume(30);
        r2.setEngineRpm(2300);
        r2.setEngineHp(12);
        r2.setEngineCoolantLow(true);
        r2.setCruiseControlOn(false);
        readingRepo.save(r1);
        readingRepo.save(r2);
    }

    @AfterEach
    void tearDown() {
        readingRepo.deleteAll();
        vehicleRepo.deleteAll();
        tireRepo.deleteAll();
        alertRepo.deleteAll();
    }

    @Test
    void addVehicles() throws Exception {
        ObjectMapper o = new ObjectMapper();
        Vehicle v = new Vehicle();
        v.setVin("Vin1");
        v.setMake("make1");
        v.setYear(2020);
        v.setRedlineRpm(500);
        v.setModel("m1");
        v.setMaxFuelVolume(50);
        v.setLastServiceDate("2020-06-26T21:01:25.268Z");
        List<Vehicle> l = new ArrayList<>();
        l.add(v);
        mvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(o.writeValueAsBytes(l)))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

    }

    @Test
    void addReading() throws Exception {
        ObjectMapper o = new ObjectMapper();
        VehicleReading r2 = new VehicleReading();
        r2.setVin("Vin1");
        r2.setCheckEngineLightOn(true);
        VehicleTires t2 = new VehicleTires(32,33,34,32);
        r2.setTires(t2);
        r2.setTimestamp("2020-06-26T21:05:25.268Z");
        r2.setSpeed(56);
        r2.setLongitude((float) 54.3425);
        r2.setLatitude((float) 45.8743);
        r2.setFuelVolume(30);
        r2.setEngineRpm(2300);
        r2.setEngineHp(12);
        r2.setEngineCoolantLow(true);
        r2.setCruiseControlOn(false);
        mvc.perform(MockMvcRequestBuilders.post("/readings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(o.writeValueAsBytes(r2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

    @Test
    void findAllVehicles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/vehicles"))
        .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    void findAllAlerts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alert/Vin1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    void findAllAlertsForNonExistentVehicle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alert/abcd"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    void findHighAlert() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/alert/high"))
        .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    void findLocation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/location/Vin1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

    @Test
    void findLocationForNonExistentVehicle() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/location/abcd"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }
}