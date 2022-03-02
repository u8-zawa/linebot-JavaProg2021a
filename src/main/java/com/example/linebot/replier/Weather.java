package com.example.linebot.replier;

import com.example.linebot.value.weatherItem.WeatherItem;
import com.linecorp.bot.model.action.LocationAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

// 天気の情報を返すための返信クラス
public class Weather implements Replier {

    private final WeatherItem item;

    public Weather(WeatherItem item) {
        this.item = item;
    }

    @Override
    public List<Message> reply() {
        String region = item.getName();
        String icon = item.getWeather().get(0).getIcon();
        String description = item.getWeather().get(0).getDescription();
        double tempMax = item.getMain().getTempMax();
        double tempMin = item.getMain().getTempMin();
        double humidity = item.getMain().getHumidity();

        Text title = Text.builder()
                .text(region + " の天気")
                .size(FlexFontSize.LG)
                .weight(Text.TextWeight.BOLD)
                .build();
        Image image = Image.builder()
                .url(URI.create("https://openweathermap.org/img/wn/" + icon + "@2x.png"))
                .size(Image.ImageSize.LG)
                .build();
        Text weather = Text.builder()
                .text(description)
                .margin(FlexMarginSize.SM)
                .flex(0)
                .align(FlexAlign.CENTER)
                .weight(Text.TextWeight.BOLD)
                .build();
        Icon icon1 = Icon.builder()
                .url(URI.create("https://user-images.githubusercontent.com/23183700/71070786-d3d9f180-21be-11ea-8319-4a8632adaf6d.png"))
                .offsetTop("2px")
                .build();
        Icon icon2 = Icon.builder()
                .url(URI.create("https://user-images.githubusercontent.com/23183700/71070988-22878b80-21bf-11ea-8505-e76db18aa499.png"))
                .offsetTop("2px")
                .build();
        Text tempMax1 = Text.builder()
                .text("最高気温")
                .margin(FlexMarginSize.SM)
                .flex(0)
                .build();
        Text tempMax2 = Text.builder()
                .text(tempMax + "℃")
                .size(FlexFontSize.SM)
                .align(FlexAlign.END)
                .color("#ff0000")
                .build();
        Text tempMin1 = Text.builder()
                .text("最低気温")
                .margin(FlexMarginSize.SM)
                .flex(0)
                .build();
        Text tempMin2 = Text.builder()
                .text(tempMin + "℃")
                .size(FlexFontSize.SM)
                .align(FlexAlign.END)
                .color("#002aff")
                .build();
        Text humidity1 = Text.builder()
                .text("湿度")
                .margin(FlexMarginSize.SM)
                .flex(0)
                .build();
        Text humidity2 = Text.builder()
                .text(humidity + "%")
                .size(FlexFontSize.SM)
                .align(FlexAlign.END)
                .build();
        Box tempMaxBox = Box.builder()
                .layout(FlexLayout.BASELINE)
                .contents(icon1, tempMax1, tempMax2)
                .build();
        Box tempMinBox = Box.builder()
                .layout(FlexLayout.BASELINE)
                .contents(icon1, tempMin1, tempMin2)
                .build();
        Box humidityBox = Box.builder()
                .layout(FlexLayout.BASELINE)
                .contents(icon2, humidity1, humidity2)
                .build();
        Box box = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(tempMaxBox, tempMinBox, humidityBox)
                .build();
        Box body = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(title, image, weather, box)
                .build();
        Bubble bubble = Bubble.builder().body(body).build();
        FlexMessage flexMessage = new FlexMessage("天気", bubble);
        return Collections.singletonList(flexMessage);
    }

    public static List<Message> guideReply() {
        String text = "どこの天気を知りたいですか？";
        List<QuickReplyItem> items = asList(
                QuickReplyItem.builder()
                        .action(LocationAction.withLabel("場所を指定する"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("札幌市", "札幌市の天気"))
                        .build(),
                QuickReplyItem.builder()
                        .action(new MessageAction("北海道", "北海道の天気"))
                        .build()
        );
        TextMessage textMessage = new TextMessage(text, QuickReply.items(items));
        return Collections.singletonList(textMessage);
    }

}
