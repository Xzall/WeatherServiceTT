package com.app.weather.services.weatherservicett.service;

import com.app.weather.services.weatherservicett.model.Message;

public interface WeatherProcessingService {
    String processMessage(Message message) throws Exception;

}
