package com.example.linebot.replier;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;

import java.util.Collections;
import java.util.List;

public class Usage implements Replier {

    @Override
    public List<Message> reply() {
        Text text = Text.builder().text("どの使い方が知りたいですか？").build();
        Button janken = Button.builder()
                .action(PostbackAction.builder()
                        .label("じゃんけん")
                        .data("USAGE_JANKEN")
                        .displayText("じゃんけん")
                        .build())
                .style(Button.ButtonStyle.SECONDARY)
                .build();
        Button weather = Button.builder()
                .action(PostbackAction.builder()
                        .label("お天気")
                        .data("USAGE_WEATHER")
                        .displayText("お天気")
                        .build())
                .style(Button.ButtonStyle.SECONDARY)
                .build();
        Button translate = Button.builder()
                .action(PostbackAction.builder()
                        .label("翻訳")
                        .data("USAGE_TRANSLATE")
                        .displayText("翻訳")
                        .build())
                .style(Button.ButtonStyle.SECONDARY)
                .build();
        Separator separator = Separator.builder().margin(FlexMarginSize.SM).build();
        Box body = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .content(text)
                .build();
        Box footer = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(janken,
                        separator,
                        weather,
                        separator,
                        translate)
                .build();
        Bubble bubble = Bubble.builder()
                .body(body)
                .footer(footer)
                .build();
        FlexMessage flexMessage = new FlexMessage("使い方", bubble);
        return Collections.singletonList(flexMessage);
    }

    public static List<Message> janken() {
        String text = "＜じゃんけんの使い方＞\n\n" +
                "①「じゃんけん」や「じゃんけんしようぜ」などのじゃんけんから始まる言葉を送信する\n" +
                "↓\n" +
                "② 自分が出す手を選択肢から選ぶ\n" +
                "↓\n" +
                "③ 相手の手と勝敗が返信される";
        TextMessage textMessage = new TextMessage(text);
        return Collections.singletonList(textMessage);
    }
    public static List<Message> weather() {
        String text = "＜お天気の使い方＞\n\n" +
                "①「”地域名” + の天気」や 位置情報メッセージ を送信する\n" +
                "↓\n" +
                "② その地域の天気が返信される";
        TextMessage textMessage = new TextMessage(text);
        return Collections.singletonList(textMessage);
    }
    public static List<Message> translate() {
        String text = "＜翻訳の使い方＞\n\n" +
                "① じゃんけんから始まる言葉 や 「”地域名” + の天気」以外の言葉を送信する\n" +
                "↓\n" +
                "② 翻訳後（英語 or 中国語 or 韓国語）の文章が送られてくる";
        TextMessage textMessage = new TextMessage(text);
        return Collections.singletonList(textMessage);
    }

}
