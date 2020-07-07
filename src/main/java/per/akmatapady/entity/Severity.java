package per.akmatapady.entity;

public enum Severity
{
    HIGH("HIGH"), MEDIUM("MEDIUM"), LOW("LOW");

    private String action;

    public String getAction() {
        return action;
    }

    Severity(String action) {
        this.action = action;
    }
}