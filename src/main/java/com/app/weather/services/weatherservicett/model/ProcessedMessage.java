package com.app.weather.services.weatherservicett.model;

import lombok.*;

@Data
public class ProcessedMessage {
    private String msg;
    private String lng;
    private double lat;
    private double lon;
    private WeatherData weatherData;

    public ProcessedMessage(Message message, WeatherData weatherData) {
        this.msg = message.getMsg();
        this.lng = message.getLng();
        this.lat = message.getLat();
        this.lon = message.getLon();
        this.weatherData = weatherData;
    }
}