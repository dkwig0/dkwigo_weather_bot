package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CallbackResponse {

    BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment);

    default InlineKeyboardMarkup getTimePicker(String time) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(Arrays.asList(InlineKeyboardButton.builder()
                .text("▲")
                .callbackData("time_plus")
                .build()));
        keyboard.add(Arrays.asList(InlineKeyboardButton.builder()
                .text(time)
                .callbackData("time_reset")
                .build()));
        keyboard.add(Arrays.asList(InlineKeyboardButton.builder()
                .text("▼")
                .callbackData("time_minus")
                .build()));
        keyboard.add(Arrays.asList(InlineKeyboardButton.builder()
                .text("subscribe")
                .callbackData("subscribe")
                .build()));
        return new InlineKeyboardMarkup(keyboard);
    }

    default String getTimePickerTime(InlineKeyboardMarkup markup) {
        return markup.getKeyboard().get(1).get(0).getText();
    }

}
