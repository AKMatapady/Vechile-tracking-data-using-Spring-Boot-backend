package per.akmatapady.entity;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    private String vin;
    private String make;
    private String model;
    private String year;
    private int readlineRPM;
    private float maxFuelVolume;
    private String lastServiceDate;

    public Vehicle() {
    }

    public Vehicle(String vin, String make, String model, String year, int readlineRPM, float maxFuelVolume, String lastServiceDate) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.readlineRPM = readlineRPM;
        this.maxFuelVolume = maxFuelVolume;
        this.lastServiceDate = lastServiceDate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getReadlineRPM() {
        return readlineRPM;
    }

    public void setReadlineRPM(int readlineRPM) {
        this.readlineRPM = readlineRPM;
    }

    public float getMaxFuelVolume() {
        return maxFuelVolume;
    }

    public void setMaxFuelVolume(float maxFuelVolume) {
        this.maxFuelVolume = maxFuelVolume;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }
}
