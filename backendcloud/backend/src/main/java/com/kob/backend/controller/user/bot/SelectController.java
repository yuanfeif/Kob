package com.kob.backend.controller.user.bot;

import com.kob.backend.pojo.Bot;
import com.kob.backend.service.user.bot.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SelectController {
    @Autowired
    private SelectService selectService;

    @GetMapping("/user/bot/select/")
    List<Bot> select() {
        return selectService.select();
    }
}
