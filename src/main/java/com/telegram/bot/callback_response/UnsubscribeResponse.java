package com.telegram.bot.callback_response;

import com.telegram.bot.db.Subscription;
import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnsubscribeResponse implements CallbackResponse {

    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {

        for (Subscription sub : repository.findAllByChatIdAndCityName(
                update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getMessage().getText().split("\'")[1]
        )) {
            repository.delete(sub);
        }
        return null;
    }
}
