package com.example.linebot.value.weatherItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// JSONデータの日毎のデータをデシリアライズするマッピング用クラス
// JsonMaming: スネークケース abc_de のキーと、キャメルケース abcDe のフィールド名をマッピングする設定
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Wind {

    private final double speed; // 風速, m/h
    private final double deg;   // 風向, 度

    @JsonCreator
    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
