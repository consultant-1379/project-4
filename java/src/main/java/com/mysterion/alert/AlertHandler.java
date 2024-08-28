package com.mysterion.alert;

import java.util.HashMap;
import java.util.Map;

public class AlertHandler {

    Map<SeverityLevel, Integer> severityCount = new HashMap<>();

    public void setSeverityCount(SeverityLevel severity, int value) {
        severityCount.put(severity, value);
    }

    public int getSeverityLevelCount (SeverityLevel severity) {
        return severityCount.get(severity);
    }

    public Map<SeverityLevel, Integer> getSeverityCount() {
        return severityCount;
    }

}
