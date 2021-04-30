package com.telegram.bot.callback_response;

import com.telegram.bot.db.Subscription;
import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SubscribeResponse implements CallbackResponse {
    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {

        String[] time = update.getCallbackQuery().getMessage().getReplyMarkup()
                .getKeyboard().get(1).get(0).getText().split(":");

        repository.save(new Subscription(
                update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getMessage().getText().split("\'")[1],
                Byte.parseByte(time[0]),
                Byte.parseByte(time[1]),
                update.getCallbackQuery().getFrom().getLanguageCode()
                ));

        return null;
    }
}
