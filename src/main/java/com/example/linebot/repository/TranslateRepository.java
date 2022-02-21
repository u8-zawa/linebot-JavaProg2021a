package com.example.linebot.repository;

import com.example.linebot.value.TranslateItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Repository
public class TranslateRepository {

    private final RestTemplate restTemplate;
    private final Properties properties;
    private final String source = "ja"; // 翻訳前
    private String target = "en"; // 翻訳後

    @Autowired
    public TranslateRepository(RestTemplateBuilder templateBuilder) throws IOException {
        this.restTemplate = templateBuilder.build();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(inputStream);
        }
    }

    public TranslateItem doTranslation(String text) {
        String apiEndPoint = properties.getProperty("translate.api-end-point");
        String url = String.format(apiEndPoint, text, source, target);
        return restTemplate.getForObject(url, TranslateItem.class);
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
