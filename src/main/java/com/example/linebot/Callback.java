package com.example.linebot;

import com.example.linebot.replier.*;
import com.example.linebot.service.ExternalService;
import com.example.linebot.service.UserService;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);
    private final ExternalService externalService;
    private final UserService userService;

    @Autowired
    public Callback(ExternalService externalService, UserService userService) {
        this.externalService = externalService;
        this.userService = userService;
    }

    // FollowEvent に対応する
    @EventMapping
    public List<Message> handleFollow(FollowEvent event) {
        log.info("Follow: {}", event);
        String userId = event.getSource().getUserId();
        Follow follow = userService.follow(userId);
        return follow.reply();
    }

    // UnfollowEvent に対応する
    @EventMapping
    public void handleUnfollow(UnfollowEvent event) {
        log.info("Unfollow: {}", event);
        String userId = event.getSource().getUserId();
        userService.unFollow(userId);
    }

    // TextMessageEvent に対応する
    @EventMapping
    public List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        log.info("TextMessage: {}", event);
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        Intent intent = Intent.whitchIntent(text);
        switch (intent) {
            case JANKEN:
                Janken janken = new Janken();
                return janken.reply();
            case WEATHER:
                Weather weather = externalService.doReplyWithWeather(text);
                return weather.reply();
            case UNKNOWN:
            default:
                Translate translate = externalService.doReplyWithTranslate(text);
                return translate.reply();
        }
    }

    // LocationMessageEvent に対応する
    @EventMapping
    public List<Message> handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        log.info("LocationMessage: {}", event);
        LocationMessageContent lmc = event.getMessage();
        double lon = lmc.getLongitude();
        double lat = lmc.getLatitude();
        Weather weather = externalService.doReplyWithWeather(lon, lat);
        return weather.reply();
    }

    // PostbackEvent に対応する
    @EventMapping
    public List<Message> handlePostback(PostbackEvent event) {
        log.info("Postback: {}", event);
        String data = event.getPostbackContent().getData();
        switch (data) {
            case "USAGE":
                Usage usage = new Usage();
                return usage.reply();
            case "USAGE_JANKEN":
                return Usage.janken();
            case "USAGE_WEATHER":
                return Usage.weather();
            case "USAGE_TRANSLATE":
                return Usage.translate();
            case "WEATHER_GUIDE":
                return Weather.guideReply();
            case "TRANSLATE_TARGET_CHANGE":
                return Translate.changeTarget();
            case "en":
            case "zh":
            case "ko":
                return externalService.doReplyWithChangeTarget(data);
            case "ROCK":
            case "SCISSORS":
            case "PAPER":
            default:
                Janken janken = new Janken();
                return janken.resultReply(event);
        }
    }

}
