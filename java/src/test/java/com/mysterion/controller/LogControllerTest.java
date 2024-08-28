package com.mysterion.controller;

import com.mysterion.alert.SeverityLevel;
import com.mysterion.db.LogRepository;
import com.mysterion.model.LogItem;
import com.mysterion.model.VersionStat;
import org.bson.types.ObjectId;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LogController.class)
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogRepository logRepository;

    @Test
    public void getAllLogsShouldReturnLogsFromLogRepository() throws Exception {
        Mockito.when(logRepository.findAll()).thenReturn(new ArrayList<>());
        this.mockMvc.perform(get("/allLogs")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void getAllStatsShouldReturnStatsOfLogs() throws Exception {
        LogItem log1 = new LogItem(new ObjectId(), SeverityLevel.WARNING.toString(),"THis is a warning","v1.0.1","20/7/2020","09:24:30");
        LogItem log2 = new LogItem(new ObjectId(), "ERROR","An Error occurred. Please fix before continuing.","v1.0.1","20/7/2020","09:24:30");
        LogItem log3 = new LogItem(new ObjectId(), "INFO","Everything is running fine","v1.0.1","20/7/2020","09:24:30");
        ArrayList<LogItem> logList = new ArrayList<>();
        logList.add(log1);
        logList.add(log2);
        logList.add(log3);
        List<VersionStat> expected = new ArrayList<>();
        VersionStat version1 = new VersionStat(log1.getVersion());
        version1.addWarning();
        expected.add(version1);
        VersionStat version2 = new VersionStat(log2.getVersion());
        version2.addError();
        expected.add(version2);
        VersionStat version3 = new VersionStat(log3.getVersion());
        version3.addInfo();
        expected.add(version3);
        Mockito.when(logRepository.findAll()).thenReturn(logList);
        this.mockMvc.perform(get("/versionStats")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(expected.toString()));
    }

}
