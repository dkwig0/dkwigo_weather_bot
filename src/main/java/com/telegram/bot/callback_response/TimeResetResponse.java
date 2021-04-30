package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TimeResetResponse implements CallbackResponse {
    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {
        EditMessageText resetTimeResponse = new EditMessageText();

        resetTimeResponse.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        resetTimeResponse.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        resetTimeResponse.setText(update.getCallbackQuery().getMessage().getText());

        resetTimeResponse.setReplyMarkup(getTimePicker("00:00"));

        return resetTimeResponse;
    }
}
