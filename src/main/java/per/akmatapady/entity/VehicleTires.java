package per.akmatapady.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class VehicleTires {
    @Id
    private String id;
    private float frontLeft;
    private float frontRight;
    private float rearLeft;
    private float rearRight;

    public VehicleTires() {
        id = UUID.randomUUID().toString();
    }

    public VehicleTires(float frontLeft, float frontRight, float rearLeft, float rearRight) {
        id = UUID.randomUUID().toString();
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getFrontLeft() {
        return frontLeft;
    }

    public void setFrontLeft(float frontLeft) {
        this.frontLeft = frontLeft;
    }

    public float getFrontRight() {
        return frontRight;
    }

    public void setFrontRight(float frontRight) {
        this.frontRight = frontRight;
    }

    public float getRearLeft() {
        return rearLeft;
    }

    public void setRearLeft(float rearLeft) {
        this.rearLeft = rearLeft;
    }

    public float getRearRight() {
        return rearRight;
    }

    public void setRearRight(float rearRight) {
        this.rearRight = rearRight;
    }

    public float getMinTirePressure() {
        return Math.min(frontLeft, Math.min(frontRight, Math.min(rearLeft, rearRight)));
    }

    public float getMaxTirePressure() {
        return Math.max(frontLeft, Math.max(frontRight, Math.max(rearLeft, rearRight)));
    }
}
