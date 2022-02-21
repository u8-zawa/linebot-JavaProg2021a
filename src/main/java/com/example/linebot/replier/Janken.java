package com.example.linebot.replier;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Image;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.linebot.replier.EmojiUtil.*;
import static java.util.Arrays.asList;

// じゃんけんに関することを返すための返信クラス
public class Janken implements Replier {

    @Override
    public List<Message> reply() {
        List<Message> replies = new ArrayList<>();
        replies.add(new TextMessage("いいだろう、かかってこい！"));

        Text text = Text.builder().text("何を出しますか？").build();
        Image rock = Image.builder()
                .action(PostbackAction.builder()
                        .label("グー")
                        .data("ROCK")
                        .displayText(EMOJI_ROCK)
                        .build())
                .url(URI.create("https://2.bp.blogspot.com/-VhlO-Yfjy_E/Uab3z3RNJQI/AAAAAAAAUVg/fX8VnSVDlWs/s800/janken_gu.png"))
                .size("25%")
                .build();
        Image scissors = Image.builder()
                .action(PostbackAction.builder()
                        .label("チョキ")
                        .data("SCISSORS")
                        .displayText(EMOJI_SCISSORS)
                        .build())
                .url(URI.create("https://4.bp.blogspot.com/-__yEIXe5SxU/Uab3zO7BB2I/AAAAAAAAUVI/MYg6TVeiv-Y/s800/janken_choki.png"))
                .size("25%")
                .build();
        Image paper = Image.builder()
                .action(PostbackAction.builder()
                        .label("パー")
                        .data("PAPER")
                        .displayText(EMOJI_PAPER)
                        .build())
                .url(URI.create("https://3.bp.blogspot.com/-qZtyoue9xKs/Uab30IG0Q5I/AAAAAAAAUVk/qnH8a2OgrvI/s800/janken_pa.png"))
                .size("25%")
                .build();
        Box body = Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(asList(text, rock, scissors, paper))
                .build();
        Bubble bubble = Bubble.builder().body(body).build();
        FlexMessage flexMessage = new FlexMessage("じゃんけん", bubble);
        replies.add(flexMessage);
        return replies;
    }

    public List<Message> resultReply(PostbackEvent event) {
        List<Message> replies = new ArrayList<>();

        String win = "お前の勝ちだ。くそっ...";
        String lose = "お前の負けだ。所詮そんなもんか笑";
        String draw = "あいこだ。仲良しかよ！";

        String myHand = event.getPostbackContent().getData();
        int rn = new Random().nextInt(3);
        String result;
        switch (rn) {
            case 0:
                replies.add(new TextMessage(EMOJI_ROCK));
                switch (myHand) {
                    case "ROCK":
                        result = draw;
                        break;
                    case "SCISSORS":
                        result = lose;
                        break;
                    case "PAPER":
                    default:
                        result = win;
                }
                replies.add(new TextMessage(result));
                break;
            case 1:
                replies.add(new TextMessage(EMOJI_SCISSORS));
                switch (myHand) {
                    case "ROCK":
                        result = win;
                        break;
                    case "SCISSORS":
                        result = draw;
                        break;
                    case "PAPER":
                    default:
                        result = lose;
                }
                replies.add(new TextMessage(result));
                break;
            case 2:
            default:
                replies.add(new TextMessage(EMOJI_PAPER));
                switch (myHand) {
                    case "ROCK":
                        result = lose;
                        break;
                    case "SCISSORS":
                        result = win;
                        break;
                    case "PAPER":
                    default:
                        result = draw;
                }
                replies.add(new TextMessage(result));
        }
        return replies;
    }

}
