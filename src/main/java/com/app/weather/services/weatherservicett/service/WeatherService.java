package com.app.weather.services.weatherservicett.service;

import com.app.weather.services.weatherservicett.model.WeatherData;

public interface WeatherService {
    WeatherData getData(double lat, double lon);
}