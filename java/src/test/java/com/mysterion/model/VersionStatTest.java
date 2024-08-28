package com.mysterion.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class VersionStatTest {

    private VersionStat versionStatTest;

    @BeforeEach
    public void setUp(){
        versionStatTest = new VersionStat("v1.0.1");
    }

    @Test
    public void testVersionStatConstructor(){
        versionStatTest = new VersionStat("v1.0.2");
        assertEquals("v1.0.2",versionStatTest.getVersionName());
    }

    @Test
    public void testGetVersionName(){
        assertEquals("v1.0.1",versionStatTest.getVersionName());
    }

    @Test
    public void testSetVersionName(){
        versionStatTest.setVersionName("v1.0.2");
        assertEquals("v1.0.2",versionStatTest.getVersionName());
    }

    @Test
    public void testGetInfo(){
        assertEquals(0,versionStatTest.getInfo());
    }

    @Test
    public void testGetError(){
        assertEquals(0,versionStatTest.getError());
    }

    @Test
    public void testGetWarning(){
        assertEquals(0,versionStatTest.getWarning());
    }

    @Test
    public void testAddInfo(){
        versionStatTest.addInfo();
        assertEquals(1,versionStatTest.getInfo());
    }

    @Test
    public void testAddError(){
        versionStatTest.addError();
        assertEquals(1,versionStatTest.getError());
    }

    @Test
    public void testAddWarning(){
        versionStatTest.addWarning();
        assertEquals(1,versionStatTest.getWarning());
    }


}
