package com.mysterion.controller;

import com.mysterion.db.AlertLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AnalyticController {

    @Autowired
    private AlertLogRepository alertLogRepo;

    @GetMapping("/allError")
    public  @ResponseBody
    Object allAlertError() {
        return alertLogRepo.findBySeverity("ERROR");
    }

    @GetMapping("/allAlert")
    public  @ResponseBody
    Object allAlert() {
        return alertLogRepo.findAll();
    }
}
