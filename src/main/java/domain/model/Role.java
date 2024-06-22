package domain.model;

public enum Role {
    DIRECTOR("director"), TEAMLEADER("teamleader"), EMPLOYEE("employee");

    private String stringValue;

    private Role(String stringValue){
        this.stringValue=stringValue;
    }

    public String getStringValue() {return stringValue;}
}
