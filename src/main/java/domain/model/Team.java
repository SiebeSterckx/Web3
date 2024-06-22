package domain.model;

public enum Team {
    ALPHA("alpha"), BETA("beta"), GAMMA("gamma"), DELTA("delta"), EPSILON("epsilon");

    private String stringValue;

    private Team(String stringValue) {
        this.stringValue=stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
