package com.app.weather.services.weatherservicett.controller;

import com.app.weather.services.weatherservicett.model.Message;
import com.app.weather.services.weatherservicett.service.WeatherProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/adapter")
public class AdapterController {
    private final WeatherProcessingService weatherProcessingService;

    @PostMapping("/processedMessage")
    public ResponseEntity<String> processMessage(@RequestBody Message message) {
        try {
            String result = weatherProcessingService.processMessage(message);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}