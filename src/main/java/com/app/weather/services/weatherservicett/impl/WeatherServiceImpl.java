package com.app.weather.services.weatherservicett.impl;

import com.app.weather.services.weatherservicett.model.WeatherData;
import com.app.weather.services.weatherservicett.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;

    private WebClient getWebClient() {
        if (webClient == null) {
            webClient = webClientBuilder.baseUrl("https://www.gismeteo.ru/api/").build();
        }
        return webClient;
    }

    @Override
    public WeatherData getData(double lat, double lon){
        //Запрос к сервису погоды
        return getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather/current")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .build())
                .retrieve().bodyToMono(WeatherData.class).block();
    }
}