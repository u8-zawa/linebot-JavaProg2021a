package com.example.linebot.value.weatherItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Main {

    private final double tempMin;  // 最低気温, ℃
    private final double tempMax;  // 最高気温, ℃
    private final double humidity; // 湿度, %

    @JsonCreator
    public Main(double tempMin, double tempMax, double humidity) {
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getHumidity() {
        return humidity;
    }
}
