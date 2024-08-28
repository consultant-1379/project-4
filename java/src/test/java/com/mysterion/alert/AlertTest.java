package com.mysterion.alert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;

public class AlertTest {

    @Id
    private String id;

    private Alert alertTest;
    private String alertInsert;

    @BeforeEach
    public void setUp() {
        alertTest = new Alert();
        alertTest.setMessage("Something is wrong");
        alertTest.setSeverity(SeverityLevel.WARNING);
        alertTest.setVersion("v1.0.1");
        alertInsert = String.format("Alert[id=%s, severity=%s, version=%s message=%s]",
                id, alertTest.getSeverity(), alertTest.getVersion(), alertTest.getMessage());
    }

    @Test
    public void testAlertConstructor() {
        alertTest = new Alert(SeverityLevel.CRITICAL,"v2.01","Update Failed");
        assertEquals(String.format("Alert[id=%s, severity=%s, version=%s message=%s]",
                this.id, alertTest.getSeverity(), alertTest.getVersion(), alertTest.getMessage()),alertTest.toString());
    }

    @Test
    public void testgetVersion() {
        assertEquals("v1.0.1",alertTest.getVersion());
    }

    @Test
    public void testgetSeverity() {
        assertEquals(SeverityLevel.WARNING,alertTest.getSeverity());
    }

    @Test
    public void testgetMessage() {
        assertEquals("Something is wrong",alertTest.getMessage());
    }

    @Test
    public void testEqual() {
        assertThat(alertInsert, is(alertTest.toString()));
    }

}