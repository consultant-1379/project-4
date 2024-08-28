package com.mysterion.controller;

import com.mysterion.db.LogRepository;
import com.mysterion.model.LogItem;
import com.mysterion.model.VersionStat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @CrossOrigin
    @GetMapping("/allLogs")
    public @ResponseBody
    Object getAllLogs() {
        return logRepository.findAll();
    }
    @CrossOrigin
    @GetMapping("/versionStats")
    public @ResponseBody
    Object getAllLogsStats() {
        List<LogItem> logs = logRepository.findAll();
        List<VersionStat> versionStats = new ArrayList<>();
        List<String> versions = new ArrayList<>();
        for (LogItem item : logs) {
            if (!versions.contains(item.getVersion())) {
                versionStats.add(new VersionStat(item.getVersion()));
                versions.add(item.getVersion());
            }
            for (int ver = 0; ver < versions.size(); ver++) {
                if (item.getVersion().equals(versions.get(ver))) {
                    switch (item.getSeverity()) {
                        case "ERROR":
                            versionStats.get(ver).addError();
                            break;
                        case "WARNING":
                            versionStats.get(ver).addWarning();
                            break;
                        case "INFO":
                            versionStats.get(ver).addInfo();
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }
        return ResponseEntity.ok().body(versionStats);

    }


}
