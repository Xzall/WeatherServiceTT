package com.app.weather.services.weatherservicett.impl;


import com.app.weather.services.weatherservicett.model.ProcessedMessage;
import com.app.weather.services.weatherservicett.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ServiceB implements MessageSender {

    private final WebClient webClient;

    @Autowired
    public ServiceB(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://Service-B-URL").build();
    }

    @Override
    public void sendMessage(ProcessedMessage message){
        webClient.post()
                .uri("/messages")
                .bodyValue(message)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}