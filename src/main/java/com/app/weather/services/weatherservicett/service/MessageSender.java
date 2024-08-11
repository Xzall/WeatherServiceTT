package com.app.weather.services.weatherservicett.service;

import com.app.weather.services.weatherservicett.model.ProcessedMessage;

public interface MessageSender {
    void sendMessage(ProcessedMessage message);
}