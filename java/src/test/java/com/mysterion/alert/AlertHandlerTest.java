package com.mysterion.alert;

import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AlertHandlerTest {

    private AlertHandler alertHandler;
    private Map<SeverityLevel,Integer> map;

    @DisplayName("Setting up AlertHandler class for test")
    @BeforeEach
    public void setUp() {
        alertHandler = new AlertHandler();
        map = new HashMap<SeverityLevel, Integer>();

        alertHandler.setSeverityCount(SeverityLevel.CRITICAL, 3);
        alertHandler.setSeverityCount(SeverityLevel.MAJOR, 2);
        alertHandler.setSeverityCount(SeverityLevel.MINOR, 0);
        alertHandler.setSeverityCount(SeverityLevel.WARNING, 10);
        alertHandler.setSeverityCount(SeverityLevel.INFO, 5);

        map.put(SeverityLevel.INFO, 5);
        map.put(SeverityLevel.WARNING, 10);
        map.put(SeverityLevel.MAJOR, 2);
        map.put(SeverityLevel.MINOR, 0);
        map.put(SeverityLevel.CRITICAL, 3);
    }

    @DisplayName("Testing map for equal, ignoring order")
    @Test
    public void testEqual() {
        assertThat(alertHandler.severityCount, is(map));
    }

    @DisplayName("Testing severityCount map size")
    @Test
    public void testMapSize() {
        assertThat(map.size(), is(5));
    }

    @DisplayName("Testing severityCount map entry")
    @Test
    public void testMapEntry() {
        assertThat(alertHandler.getSeverityCount(), IsMapContaining.hasEntry(SeverityLevel.INFO, 5));
        assertThat(alertHandler.getSeverityCount(), not(IsMapContaining.hasEntry(SeverityLevel.MINOR, 1)));
    }

    @DisplayName("Testing severityCount map key")
    @Test
    public void testMapKey() {
        assertThat(alertHandler.getSeverityCount(), IsMapContaining.hasKey(SeverityLevel.CRITICAL));
    }

    @DisplayName("Testing severityCount map value")
    @Test
    public void testMapValue() {
        assertThat(alertHandler.getSeverityCount(), IsMapContaining.hasValue(10));
    }

    @DisplayName("Testing severity count level")
    @Test
    public void testGetSeverityLevelCount() {
        assertThat(alertHandler.getSeverityLevelCount(SeverityLevel.MAJOR), is(2));
    }

}