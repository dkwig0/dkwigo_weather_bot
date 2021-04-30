package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DeleteMessageResponse implements CallbackResponse {

    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {
        return new DeleteMessage(
                update.getCallbackQuery().getMessage().getChatId().toString(),
                update.getCallbackQuery().getMessage().getMessageId()
        );
    }

}
