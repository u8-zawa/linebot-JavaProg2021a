package com.example.linebot.service;

import com.example.linebot.replier.Translate;
import com.example.linebot.replier.Weather;
import com.example.linebot.repository.TranslateRepository;
import com.example.linebot.repository.WeatherRepository;
import com.example.linebot.value.TranslateItem;
import com.example.linebot.value.weatherItem.WeatherItem;
import com.example.linebot.value.WeatherSlot;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ExternalService {

    private final WeatherRepository weatherRepo;
    private final TranslateRepository translateRepo;

    @Autowired
    public ExternalService(WeatherRepository weatherRepo,
                           TranslateRepository translateRepo) {
        this.weatherRepo = weatherRepo;
        this.translateRepo = translateRepo;
    }

    public Weather doReplyWithWeather(String text) {
        WeatherSlot weatherSlot = new WeatherSlot(text);
        WeatherItem weatherItem = weatherRepo.findOpenWeatherMap(weatherSlot.getRegion());
        return new Weather(weatherItem);
    }

    public Weather doReplyWithWeather(double lon, double lat) {
        WeatherItem weatherItem = weatherRepo.findOpenWeatherMap(lon, lat);
        weatherItem.setName("指定された場所");
        return new Weather(weatherItem);
    }

    public Translate doReplyWithTranslate(String text) {
        TranslateItem translateItem = translateRepo.doTranslation(text);
        return new Translate(translateItem);
    }

    public List<Message> doReplyWithChangeTarget(String target) {
        translateRepo.setTarget(target);
        String lang;
        switch (target) {
            case "en":
                lang = "英語";
                break;
            case "zh":
                lang = "中国語";
                break;
            case "ko":
            default:
                lang = "韓国語";
        }
        TextMessage textMessage = new TextMessage("言語を「" + lang + "」に変更しました");
        return Collections.singletonList(textMessage);
    }

}
