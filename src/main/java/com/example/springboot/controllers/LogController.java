package com.example.springboot.controllers;

import com.example.springboot.services.LogAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogAnalyzerService analyzerService;

    public LogController(LogAnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestBody String log) {
        String result = analyzerService.analyzeLog(log);
        return ResponseEntity.ok(result);
    }
}
