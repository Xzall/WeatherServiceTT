package com.app.weather.services.weatherservicett.impl;

import com.app.weather.services.weatherservicett.constants.ApplicationMessages;
import com.app.weather.services.weatherservicett.model.Message;
import com.app.weather.services.weatherservicett.model.ProcessedMessage;
import com.app.weather.services.weatherservicett.model.WeatherData;
import com.app.weather.services.weatherservicett.service.WeatherProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherProcessingServiceImpl implements WeatherProcessingService {

    private final WeatherServiceImpl weatherService;
    private final ServiceB serviceB;

    @Override
    public String processMessage(Message message) throws Exception {
        if (message == null || message.getMsg() == null || message.getMsg().isEmpty()) {
            return ApplicationMessages.EMPTY_MESSAGE;
        }
        if (!"ru".equals(message.getLng())) {
            return ApplicationMessages.MESSAGE_IGNORED;
        }

        try {
            WeatherData weatherData = weatherService.getData(message.getLat(), message.getLon());
            ProcessedMessage processedMessage = new ProcessedMessage(message, weatherData);
            serviceB.sendMessage(processedMessage);
            return ApplicationMessages.MESSAGE_SENT;
        } catch (Exception e) {
            throw new Exception(ApplicationMessages.INTERNAL_ERROR, e);
        }
    }
}
