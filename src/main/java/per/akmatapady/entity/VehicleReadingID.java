package per.akmatapady.entity;

import java.io.Serializable;

public class VehicleReadingID implements Serializable {
    private String vin;
    private String timestamp;

    public VehicleReadingID() {
    }

    public VehicleReadingID(String vin, String timestamp) {
        this.vin = vin;
        this.timestamp = timestamp;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VehicleReadingID accountId = (VehicleReadingID) obj;
        return vin.equals(accountId.getVin()) &&
                timestamp.equals(accountId.getTimestamp());
    }

    @Override
    public String toString() {
        return "VehicleReadingID{" +
                "vin='" + vin + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
