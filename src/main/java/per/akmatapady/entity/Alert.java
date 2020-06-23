package per.akmatapady.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Alert {

    @Id
    private String id;
    private String vin;
    private String timestamp;
    private AlertRule alertRule;
    private Severity priority;

    public Alert() {
        id = UUID.randomUUID().toString();
    }

    public Alert(String vin, String timestamp, AlertRule alertRule, Severity priority) {
        id = UUID.randomUUID().toString();
        this.vin = vin;
        this.timestamp = timestamp;
        this.alertRule = alertRule;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public AlertRule getAlertRule() {
        return alertRule;
    }

    public void setAlertRule(AlertRule alertRule) {
        this.alertRule = alertRule;
    }

    public Severity getPriority() {
        return priority;
    }

    public void setPriority(Severity priority) {
        this.priority = priority;
    }
}
