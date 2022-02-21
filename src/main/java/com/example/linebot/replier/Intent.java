package com.example.linebot.replier;

import java.util.EnumSet;
import java.util.regex.Pattern;

public enum Intent {

    // メッセージに対応する正規表現の定義
    JANKEN("^じゃんけん.*$"),
    WEATHER("^(.*)の天気$"),
    UNKNOWN(".+");

    private final String regexp;

    Intent(String regexp) {
        this.regexp = regexp;
    }

    // メッセージからやりとり状態を判断
    public static Intent whitchIntent(String text) {
        // 全ての Intent を取得
        EnumSet<Intent> set = EnumSet.allOf(Intent.class);
        // 引数 text が、どのパターンに当てはまるかチェック
        // 当てはまったものを戻り値とする
        for (Intent intent : set) {
            if (Pattern.matches(intent.regexp, text)) {
                return intent;
            }
        }
        return UNKNOWN;
    }

    public String getRegexp() {
        return regexp;
    }

}
