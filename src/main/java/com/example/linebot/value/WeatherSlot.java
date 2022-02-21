package com.example.linebot.value;

import com.example.linebot.replier.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherSlot {

    private final String region;

    public WeatherSlot(String text) {
        // Slotにあたる部分を取り出す正規表現の仕組み（Matcher）を作る
        String regexp = Intent.WEATHER.getRegexp();
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            // テキストの中から都道府県名を抜き出す
            region = matcher.group(1);
        } else {
            // 正規表現にマッチしない場合、実行時例外を throw する
            throw new IllegalArgumentException("text をスロットに分けられません");
        }
    }

    public String getRegion() {
        return region;
    }
}
