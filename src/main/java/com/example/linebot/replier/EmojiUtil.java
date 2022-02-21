package com.example.linebot.replier;

// 絵文字を使用するためのクラス
public class EmojiUtil {

    public static String EMOJI_SMILE = codePointToString(0x1F60A);
    public static String EMOJI_ROCK = codePointToString(0x270A);
    public static String EMOJI_SCISSORS = codePointToString(0x270C);
    public static String EMOJI_PAPER = codePointToString(0x1F590);

    public static String codePointToString(int codepoint) {
        // コードポイントからchar[]への変換
        char[] chars = java.lang.Character.toChars(codepoint); // java.lang.Character
        return new String(chars);
    }

}
