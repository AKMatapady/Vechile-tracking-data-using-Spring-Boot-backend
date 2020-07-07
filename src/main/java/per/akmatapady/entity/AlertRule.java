package per.akmatapady.entity;

public enum AlertRule
{
    RPM_TOO_HIGH("HIGN_RPM"),
    LOW_FUEL("LOW_FUEL"),
    INCORRECT_TIRE_PRESSURE("INCORRECT_TIRE_PRESSURE"),
    ENGINE_CHECK_REQUIRED("CHECK_ENGINE");

    private String action;

    public String getAction() {
        return action;
    }

    private AlertRule(String action) {
        this.action = action;
    }
}