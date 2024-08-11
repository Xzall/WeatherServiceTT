package com.app.weather.services.weatherservicett;
import com.app.weather.services.weatherservicett.constants.ApplicationMessages;
import com.app.weather.services.weatherservicett.impl.ServiceB;
import com.app.weather.services.weatherservicett.impl.WeatherProcessingServiceImpl;
import com.app.weather.services.weatherservicett.impl.WeatherServiceImpl;
import com.app.weather.services.weatherservicett.model.Message;
import com.app.weather.services.weatherservicett.model.ProcessedMessage;
import com.app.weather.services.weatherservicett.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherProcessedServiceTest {

    @Mock
    private WeatherServiceImpl weatherService;

    @Mock
    private ServiceB serviceB;

    private WeatherProcessingServiceImpl weatherProcessingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherProcessingService = new WeatherProcessingServiceImpl(weatherService, serviceB);
    }

    @Test
    void processMessage_EmptyMessage_ReturnsEmptyMessageResponse() throws Exception {
        Message message = new Message();
        message.setMsg("");

        String result = weatherProcessingService.processMessage(message);

        assertEquals(ApplicationMessages.EMPTY_MESSAGE, result);
    }

    @Test
    void processMessage_NonRussianLanguage_ReturnsIgnoredMessageResponse() throws Exception {
        Message message = new Message();
        message.setMsg("Hello");
        message.setLng("en");

        String result = weatherProcessingService.processMessage(message);

        assertEquals(ApplicationMessages.MESSAGE_IGNORED, result);
    }

    @Test
    void processMessage_ValidMessage_ReturnsMessageSentResponse() throws Exception {
        Message message = new Message();
        message.setMsg("Привет");
        message.setLng("ru");
        message.setLat(55.7558);
        message.setLon(37.6173);

        WeatherData weatherData = new WeatherData("20°C", "Солнечно");
        when(weatherService.getData(anyDouble(), anyDouble())).thenReturn(weatherData);

        String result = weatherProcessingService.processMessage(message);

        assertEquals(ApplicationMessages.MESSAGE_SENT, result);
        verify(weatherService).getData(55.7558, 37.6173);
        verify(serviceB).sendMessage(any(ProcessedMessage.class));
    }

    @Test
    void processMessage_WeatherServiceThrowsException_ThrowsException() {
        Message message = new Message();
        message.setMsg("Привет");
        message.setLng("ru");
        message.setLat(55.7558);
        message.setLon(37.6173);

        when(weatherService.getData(anyDouble(), anyDouble())).thenThrow(new RuntimeException("API Error"));

        Exception exception = assertThrows(Exception.class, () -> {
            weatherProcessingService.processMessage(message);
        });

        assertEquals(ApplicationMessages.INTERNAL_ERROR, exception.getMessage());
    }
}