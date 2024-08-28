package com.mysterion;

import com.mysterion.alert.Alert;
import com.mysterion.db.AlertLogRepository;
import com.mysterion.db.LogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;


public class AppTest {

    @MockBean
    private AlertLogRepository alertLogRepository;

    @MockBean
    private LogRepository logRepository;


    @Test
    void contextLoads() {
    }

    @DisplayName("Test Spring Loads ignoring mongodb")
    @Test
    void testMain() {
        assertThrows(UnsatisfiedDependencyException.class,
                ()-> App.main(new String[] {}));
    }



    @Configuration
    @Import(Alert.class)
    static class TestConfig {
        @Bean
        AlertLogRepository alertLogRepository() {

            return mock(AlertLogRepository.class);
        }

        @Bean
        LogRepository logRepository() {
            return mock(LogRepository.class);
        }
    }
}
