package com.example.linebot.replier;

import com.linecorp.bot.model.message.Message;

import java.util.List;

// 返信用クラスのためのインターフェース
public interface Replier {

    List<Message> reply();

}
