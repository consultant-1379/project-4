package com.mysterion.model;

import com.mysterion.alert.SeverityLevel;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;
import static org.junit.Assert.assertEquals;

class LogItemTest {
    @Id
    private ObjectId id;

    private LogItem logItemTest;

    @BeforeEach
    public void setUp() {
        logItemTest = new LogItem();
        id = new ObjectId();
        logItemTest.setId(id);
        logItemTest.setMessage("Something is wrong");
        logItemTest.setSeverity(SeverityLevel.WARNING.toString());
        logItemTest.setVersion("v1.0.1");
        logItemTest.setDate("20/7/2020");
        logItemTest.setTime("09:24:30");
    }

    @Test
    void testLogItemConstructor(){
        logItemTest = new LogItem(id, SeverityLevel.MAJOR.toString(),"Something major just happened","v1.0.1","20/7/2020","09:24:30");
        assertEquals(this.id +
                        logItemTest.getSeverity() + logItemTest.getVersion() + logItemTest.getDate() + logItemTest.getMessage() + logItemTest.getTime()
                , logItemTest.toString());
    }

    @Test
    void testGetMessage(){
        assertEquals("Something is wrong",logItemTest.getMessage());
    }

    @Test
    void testGetId(){
        assertEquals(id.toString(),logItemTest.getId());
    }

    @Test
    void testGetVersion(){
        assertEquals("v1.0.1",logItemTest.getVersion());
    }

    @Test
    void testGetDate(){
        assertEquals("20/7/2020",logItemTest.getDate());
    }

    @Test
    void testGetTime(){
        assertEquals("09:24:30",logItemTest.getTime());
    }

    @Test
    void testGetSeverity(){
        assertEquals(SeverityLevel.WARNING.toString(),logItemTest.getSeverity());
    }

}
