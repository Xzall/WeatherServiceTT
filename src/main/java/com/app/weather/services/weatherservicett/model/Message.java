package com.app.weather.services.weatherservicett.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private String lng;
    private String msg;
    private double lat;
    private double lon;
}
