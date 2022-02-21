package com.example.linebot.replier;

import com.example.linebot.value.TranslateItem;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class Translate implements Replier {

    private final TranslateItem item;

    public Translate(TranslateItem item) {
        this.item = item;
    }

    @Override
    public List<Message> reply() {
        String text = item.getText();
        return Collections.singletonList(new TextMessage(text));
    }

    public static List<Message> changeTarget() {
        String text = "どの言語に翻訳しますか？";
        List<QuickReplyItem> items = asList(
                QuickReplyItem.builder()
                        .action(PostbackAction.builder()
                                .label("英語")
                                .data("en")
                                .displayText("英語")
                                .build())
                        .build(),
                QuickReplyItem.builder()
                        .action(PostbackAction.builder()
                                .label("中国語")
                                .data("zh")
                                .displayText("中国語")
                                .build())
                        .build(),
                QuickReplyItem.builder()
                        .action(PostbackAction.builder()
                                .label("韓国語")
                                .data("ko")
                                .displayText("韓国語")
                                .build())
                        .build()
        );
        TextMessage textMessage = new TextMessage(text, QuickReply.items(items));
        return Collections.singletonList(textMessage);
    }

}
