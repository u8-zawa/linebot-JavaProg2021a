package com.example.linebot.value.weatherItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

// JSONデータの日毎のデータをデシリアライズするマッピング用クラス
// JsonNaming: スネークケース abc_de のキーと、キャメルケース abcDe のフィールド名をマッピングする設定
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WeatherItem {

    private final List<Weather> weather; // 天気
    private final Main main;             // 気温など
    private final Wind wind;             // 風
    private String name;                 // 地名

    @JsonCreator
    public WeatherItem(List<Weather> weather, Main main, Wind wind, String name) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.name = name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
