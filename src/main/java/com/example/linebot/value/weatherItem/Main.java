package com.example.linebot.value.weatherItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// JSONデータの日毎のデータをデシリアライズするマッピング用クラス
// JsonMaming: スネークケース abc_de のキーと、キャメルケース abcDe のフィールド名をマッピングする設定
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Main {

    private final double temp;      // 気温, ℃
    private final double feelsLike; // 体感温度, ℃
    private final double tempMin;   // 最低気温, ℃
    private final double tempMax;   // 最高気温, ℃
    private final double pressure;  // 大気圧, hPa
    private final double humidity;  // 湿度, %

    @JsonCreator
    public Main(double temp,
                double feelsLike,
                double tempMin,
                double tempMax,
                double pressure,
                double humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }
}
