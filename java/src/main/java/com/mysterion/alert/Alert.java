package com.mysterion.alert;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

@Service
public class Alert {

    @Id
    private String id;

    private String message;
    private String version;
    private SeverityLevel severity;

    public Alert() {}

    public Alert(SeverityLevel severity, String version, String message) {
        this.severity = severity;
        this.version = version;
        this.message = message;
    }

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Alert[id=%s, severity=%s, version=%s message=%s]",
                id, severity, version, message);
    }
}
