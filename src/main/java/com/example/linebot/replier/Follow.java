package com.example.linebot.replier;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.Collections;
import java.util.List;

import static com.example.linebot.replier.EmojiUtil.EMOJI_SMILE;

// フォローされたとき用の返信クラス
public class Follow implements Replier {

    private final String displayName;

    public Follow(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public List<Message> reply() {
        String text = displayName + "さん、友達追加ありがとうございます" + EMOJI_SMILE + "\n\n"
                + "こちらのLINEBotでは、\nじゃんけん・天気・翻訳の機能があります！\n\n"
                + "是非、使って見てください！";
        return Collections.singletonList(new TextMessage(text));
    }

}
