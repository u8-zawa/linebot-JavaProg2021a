package com.example.linebot.repository;

import com.example.linebot.value.weatherItem.WeatherItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Repository
public class WeatherRepository {

    private final RestTemplate restTemplate;
    private final Properties properties;

    @Autowired
    public WeatherRepository(RestTemplateBuilder templateBuilder) throws IOException {
        this.restTemplate = templateBuilder.build();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(inputStream);
        }
    }

    public WeatherItem findOpenWeatherMap(String region) {
        String apiKey = properties.getProperty("openweathermap.api-key");
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?units=metric&q=%s&APPID=%s&lang=ja", region, apiKey);
        return restTemplate.getForObject(url, WeatherItem.class);
    }

    public WeatherItem findOpenWeatherMap(double lon, double lat) {
        String apiKey = properties.getProperty("openweathermap.api-key");
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=%f&lon=%f&APPID=%s&lang=ja", lat, lon, apiKey);
        return restTemplate.getForObject(url, WeatherItem.class);
    }

}
