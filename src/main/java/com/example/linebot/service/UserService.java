package com.example.linebot.service;

import com.example.linebot.RichMenuController;
import com.example.linebot.replier.Follow;
import com.example.linebot.repository.UserRepository;
import com.example.linebot.value.User;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private final LineMessagingClient lmc;
    private final UserRepository userRepo;
    private final RichMenuController rmc;

    @Autowired
    public UserService(LineMessagingClient lmc, UserRepository userRepo,
                       RichMenuController rmc) {
        this.lmc = lmc;
        this.userRepo = userRepo;
        this.rmc = rmc;
    }

    public Follow follow(String userId) {
        try {
            rmc.addRichMenu(userId);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            final UserProfileResponse upr = lmc.getProfile(userId).get();
            String displayName = upr.getDisplayName();
            User user = new User(userId, timestamp, displayName);

            // DBに存在する場合は、update, 存在しない場合は、insert
            if (userRepo.exists(userId)) {
                userRepo.update(user);
            } else {
                userRepo.insert(user);
            }

            return new Follow(displayName);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unFollow(String userId) {
        rmc.delRichMenu(userId);
        userRepo.delete(userId);
    }

}
