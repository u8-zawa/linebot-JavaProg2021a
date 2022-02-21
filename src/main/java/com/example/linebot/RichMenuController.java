package com.example.linebot;

import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.richmenu.RichMenu;
import com.linecorp.bot.model.richmenu.RichMenuArea;
import com.linecorp.bot.model.richmenu.RichMenuBounds;
import com.linecorp.bot.model.richmenu.RichMenuSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class RichMenuController {

    private static final Logger log = LoggerFactory.getLogger(Push.class);
    private final LineMessagingClient messagingClient;
    private final LineBlobClient blobClient;

    @Autowired
    public RichMenuController(LineMessagingClient lineMessagingClient, LineBlobClient blobClient) {
        this.messagingClient = lineMessagingClient;
        this.blobClient = blobClient;
    }

    // リッチーメニューを作成する
    public void addRichMenu(String userId) {

        // ①リッチメニューを作成
        var richMenu = RichMenu.builder()
                .name("リッチメニュー1")
                .chatBarText("メニュー")
                .areas(makeRichMenuAreas())
                .selected(true)
                .size(RichMenuSize.FULL)
                .build();

        try {

            // ②作成したリッチメニューの登録（ resp1 は作成結果で、リッチメニューIDが入っている）
            var resp1 = messagingClient.createRichMenu(richMenu).get();
            log.info("create richmenu:{}", resp1);
            // ③リッチメニューの背景画像の設定( resp2 は、画像の登録結果）
            var cpr = new ClassPathResource("/img/RichMenu.jpg");
            var fileContent = Files.readAllBytes(cpr.getFile().toPath());
            var resp2 = blobClient.setRichMenuImage(resp1.getRichMenuId(), "image/jpeg", fileContent).get();
            log.info("set richmenu image:{}", resp2);

            // ④リッチメニューIdをユーザIdとリンクする（ resp3 は、紐付け結果）
            // リンクすることで作成したリッチメニューを使えるようになる
            var resp3 = messagingClient.linkRichMenuIdToUser(userId, resp1.getRichMenuId()).get();
            log.info("link richmenu:{}", resp3);

        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delRichMenu(String userId) {
        try {

            // ①ユーザからリッチメニューを解除する（※Messaging APIで作成したものだけ）
            messagingClient.unlinkRichMenuIdFromUser(userId);

            // ②作成されているリッチメニューの取得（ resp4 は、リッチメニューの一覧情報）
            var resp4 = messagingClient.getRichMenuList().get();
            log.info("get richmenus:{}", resp4);

            // ③リッチメニューIdを指定して削除する
            // ここでは resp4 のものをすべて削除しているが、本来はリッチメニューIdと
            // ユーザIDの対応をDBなどに保存しておいて、不要なものだけを削除する
            resp4.getRichMenus().forEach(r -> messagingClient.deleteRichMenu(r.getRichMenuId()));

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // 画像のどの部分（ピクセル）に、どんな動作をするリッチメニューを割り当てるか設定します
    private List<RichMenuArea> makeRichMenuAreas() {
        final var richMenuAreas = new ArrayList<RichMenuArea>();
        richMenuAreas.add(makeMessageAction(0, 0, 833, 843, "じゃんけんしよう！"));
        richMenuAreas.add(makePostbackAction(832, 0, 836, 843, "WEATHER_GUIDE", "天気を教えて！"));
        richMenuAreas.add(makeURIAction(1668, 0, 833, 843, "カメラ翻訳", "https://line.me/R/nv/camera/ocr"));
//        richMenuAreas.add(makeMessageAction(0, 843, 833, 843, "未定（左）"));
        richMenuAreas.add(makePostbackAction(832, 843, 836, 843, "USAGE", "使い方"));
        richMenuAreas.add(makePostbackAction(1668, 843, 833, 843, "TRANSLATE_TARGET_CHANGE", "言語の変更"));
        return richMenuAreas;
    }

    // リッチメニューに MessageAction を割り当てます
    private RichMenuArea makeMessageAction(int x, int y, int w, int h, String label) {
        return new RichMenuArea(new RichMenuBounds(x, y, w, h),
                new MessageAction(label, label));
    }

    // リッチメニューに PostbackAction を割り当てます
    private RichMenuArea makePostbackAction(int x, int y, int w, int h, String data, String displayText) {
        return new RichMenuArea(new RichMenuBounds(x, y, w, h),
                PostbackAction.builder()
                        .data(data)
                        .displayText(displayText)
                        .build());
    }

    // リッチメニューに URIAction を割り当てます
    private RichMenuArea makeURIAction(int x, int y, int w, int h, String label, String uri) {
        return new RichMenuArea(new RichMenuBounds(x, y, w, h),
                new URIAction(label, URI.create(uri), new URIAction.AltUri(URI.create(uri))));
    }

}