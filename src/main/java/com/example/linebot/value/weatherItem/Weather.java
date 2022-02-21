package com.example.linebot.value.weatherItem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// JSONデータの日毎のデータをデシリアライズするマッピング用クラス
// JsonMaming: スネークケース abc_de のキーと、キャメルケース abcDe のフィールド名をマッピングする設定
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Weather {

    private final long id;            // 気象条件id
    private final String main;        // 天気（晴れ、雨、など）
    private final String description; // 天気の状況
    private final String icon;        // 天気のアイコンid

    @JsonCreator
    public Weather(long id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
