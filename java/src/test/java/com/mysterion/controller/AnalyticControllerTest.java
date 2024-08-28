package com.mysterion.controller;

import com.mysterion.db.AlertLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AnalyticController.class)
public class AnalyticControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private AlertLogRepository alertLogRepository;

    @Autowired
    private AnalyticController analyticController;

    @BeforeEach
    public void setUp() {
        analyticController = new AnalyticController();
    }

    @Test
    void testInstance() {
        assertThat(analyticController).isNotNull();
    }

    @Test
    void testAllAlertMethodNull() throws Exception {
        assertThrows(NullPointerException.class,
                ()-> when(analyticController.allAlert()).thenReturn("[]"));
        this.mockMVC.perform(get("/allAlert")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    void testAllAlertErrorMethodNull() throws Exception {
        assertThrows(NullPointerException.class,
                ()->
                    when(analyticController.allAlertError()).thenReturn("[]"));
        this.mockMVC.perform(get("/allAlert")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllErrorShouldReturnAllErrorFromAlertLogRepository() throws Exception {
        when(alertLogRepository.findBySeverity("Error")).thenReturn(new ArrayList<>());
        this.mockMVC.perform(get("/allError")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllAlertsShouldReturnLogsFromLogRepository() throws Exception {
        when(alertLogRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMVC.perform(get("/allAlert")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
