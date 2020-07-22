package per.akmatapady.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import per.akmatapady.awsmessaging.AlertNotification;
import per.akmatapady.entity.*;
import per.akmatapady.exception.ResourceNotFoundException;
import per.akmatapady.repository.*;

import java.util.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
class TrackerServiceImplTest {

    @TestConfiguration
    static class TrackerTest
    {
        @Bean
        public TrackerService track()
        {
            return new TrackerServiceImpl();
        }

        @Bean
        public AlertNotification track2(@Qualifier("getnotificationTemp") NotificationMessagingTemplate notificationMessagingTemplate)
        {
            return new AlertNotification(notificationMessagingTemplate);
        }

        @Bean
        public NotificationMessagingTemplate getnotificationTemp(AmazonSNS amazonSns)
        {
            return new NotificationMessagingTemplate(amazonSns);
        }
    }

    @MockBean
    private VehicleRepository vehicleRepo;

    @MockBean
    private ReadingRepository readingRepo;

    @MockBean
    private TireRepository tireRepo;

    @MockBean
    private AlertRepository alertRepo;

    @Autowired
    private AlertNotification notifyAlert;

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    private TrackerService TRepo;

    List<Vehicle> vList;
    Vehicle v3;
    VehicleReading r1;
    List<Alert> aList;
    List<Alert> aHighList;
    List<VehicleReading> rList;
    List<Location> locList;
    @BeforeEach
    void setUp() {
        Instant now;
        System.out.println("BeforeEach");
        vList = new ArrayList<>();
        Vehicle v1 = new Vehicle();
        v1.setVin("Vin1");
        v1.setMake("make1");
        v1.setYear(2020);
        v1.setRedlineRpm(500);
        v1.setModel("m1");
        v1.setMaxFuelVolume(50);
        v1.setLastServiceDate("2020-06-25T17:31:25.268Z");
        Vehicle v2 = new Vehicle();
        v2.setVin("Vin2");
        v2.setMake("make2");
        v2.setYear(2002);
        v2.setRedlineRpm(502);
        v2.setModel("m2");
        v2.setMaxFuelVolume(52);
        v2.setLastServiceDate("2002-06-25T17:31:25.268Z");
        vList.add(v1);
        vList.add(v2);
        v3 = new Vehicle();
        v3.setVin("Vin3");
        v3.setMake("make3");
        v3.setYear(2003);
        v3.setRedlineRpm(503);
        v3.setModel("m3");
        v3.setMaxFuelVolume(53);
        v3.setLastServiceDate("2003-06-25T12:31:25.268Z");
        vList.add(v3);
        Mockito.when(vehicleRepo.findAll()).thenReturn(vList);
        Mockito.when(vehicleRepo.saveAll(vList)).thenReturn(vList);
        for(int i = 0; i < vList.size(); i++)
            Mockito.when(vehicleRepo.findById(vList.get(i).getVin())).thenReturn(Optional.ofNullable(vList.get(i)));

        r1 = new VehicleReading();
        r1.setVin("Vin1");
        r1.setCheckEngineLightOn(false);
        r1.setTires(new VehicleTires(32,33,34,32));
        //r1.setTimestamp("2020-06-30T18:51:25.268Z");
        now = Instant.now();
        r1.setTimestamp(now.toString());
        r1.setSpeed(56);
        r1.setLongitude((float) 32.765);
        r1.setLatitude((float) 76.43242332);
        r1.setFuelVolume(32);
        r1.setEngineRpm(2000);
        r1.setEngineHp(12);
        r1.setEngineCoolantLow(false);
        r1.setCruiseControlOn(false);
        VehicleReading r2 = new VehicleReading();
        r2.setVin("Vin1");
        r2.setCheckEngineLightOn(true);
        r2.setTires(new VehicleTires(32,33,34,32));
        now = Instant.now();
        //r2.setTimestamp("2020-06-30T18:55:25.268Z");
        r2.setTimestamp(now.toString());
        r2.setSpeed(56);
        r2.setLongitude((float) 54.3425);
        r2.setLatitude((float) 45.8743);
        r2.setFuelVolume(30);
        r2.setEngineRpm(2300);
        r2.setEngineHp(12);
        r2.setEngineCoolantLow(false);
        r2.setCruiseControlOn(false);
        Mockito.when(readingRepo.save(r1)).thenReturn(r1);
        rList = new ArrayList<>();
        rList.add(r1);
        rList.add(r2);
        Mockito.when(readingRepo.findByVin("Vin1")).thenReturn(rList);

        locList = new ArrayList<>();
        Location l1 = new Location();
        l1.setLongitude(32.765);
        l1.setLatitude(76.43242332);
        Location l2 = new Location();
        l2.setLongitude(54.3425);
        l2.setLatitude(45.8743);
        locList.add(l1);
        locList.add(l2);

        aList = new ArrayList<>();
        Alert a1 = new Alert();
        a1.setPriority(Severity.MEDIUM);
        a1.setVin("Vin2");
        a1.setTimestamp("2020-06-29T12:01:25.268Z");
        a1.setAlertRule(AlertRule.LOW_FUEL);
        Alert a2 = new Alert();
        a2.setPriority(Severity.HIGH);
        a2.setVin("Vin2");
        now = Instant.now();
//        a2.setTimestamp("2020-06-30T18:52:25.268Z");
        a2.setTimestamp(now.toString());
        a2.setAlertRule(AlertRule.RPM_TOO_HIGH);
        Alert a3 = new Alert();
        a3.setPriority(Severity.LOW);
        a3.setVin("Vin2");
        a3.setTimestamp("2020-06-29T21:59:25.268Z");
        a3.setAlertRule(AlertRule.ENGINE_CHECK_REQUIRED);
        aList.add(a1);
        aList.add(a2);
        aList.add(a3);
        Mockito.when(alertRepo.findByVin("Vin2")).thenReturn(aList);
        aHighList = new ArrayList<>();
        Alert a4 = new Alert();
        a4.setPriority(Severity.HIGH);
        a4.setVin("Vin3");
        now = Instant.now();
//        a4.setTimestamp("2020-06-30T18:52:25.268Z");
        a4.setTimestamp(now.toString());
        a4.setAlertRule(AlertRule.RPM_TOO_HIGH);
        aHighList.add(a2);
        aHighList.add(a4);
        Mockito.when(alertRepo.findByPriority(Severity.HIGH)).thenReturn(aHighList);
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");

    }

    @Test
    void addVehicles() {
        List<Vehicle> res = TRepo.addVehicles(vList);
        assertEquals(vList, res);
    }

    @Test
    void addReadings() throws JsonProcessingException {
        VehicleReading r = TRepo.addReadings(r1);
        assertEquals(r1, r);
    }

    @Test
    void findOneVehicle() {
        Vehicle v = TRepo.findOneVehicle(v3.getVin());
        assertEquals(v3, v);
    }

    @Test
    void findOneNonExistingVehicle() {
//        Vehicle v = new Vehicle();
//        v.setVin("Vin");
//        v.setMake("make");
//        v.setYear(2000);
//        v.setRedlineRpm(100);
//        v.setModel("m");
//        v.setMaxFuelVolume(10);
//        v.setLastServiceDate("1996-06-25T17:31:25.268Z");
        assertThrows(ResourceNotFoundException.class,
                () -> TRepo.findOneVehicle("vin"));
    }

    @Test
    void findAllVehicles() {
        List<Vehicle> res = TRepo.findAllVehicles();
        assertEquals(vList, res);
    }

    @Test
    void findAllAlerts() {
        List<Alert> a = TRepo.findAllAlerts("Vin2");
        assertEquals(aList, a);
    }

    @Test
    void findAllAlertsForNonExistingVehicle() {
        assertThrows(ResourceNotFoundException.class,
                () -> TRepo.findAllAlerts("new vin"));
    }

    @Test
    void findHighPriority() {
        List<Alert> res = TRepo.findHighPriority();
        assertEquals(aHighList, res);
    }

    @Test
    void findLocation() {
        List<Location> l = TRepo.findLocation("Vin1");

        //assertEquals(locList, l);
        assertAll(
                () -> assertEquals(locList.get(0).getLatitude(), l.get(0).getLatitude(), 0.001),
                () -> assertEquals(locList.get(0).getLongitude(), l.get(0).getLongitude(), 0.001),
                () -> assertEquals(locList.get(1).getLatitude(), l.get(1).getLatitude(), 0.001),
                () -> assertEquals(locList.get(1).getLongitude(), l.get(1).getLongitude(), 0.001)
        );
    }

    @Test
    void findLocationForNonExistentVehicle() {
        assertThrows(ResourceNotFoundException.class,
                () -> TRepo.findLocation("abc"));
    }
}