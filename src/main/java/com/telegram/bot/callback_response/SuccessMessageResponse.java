package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public class SuccessMessageResponse implements CallbackResponse {
    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {
        EditMessageText successMessageResponse = new EditMessageText();

        successMessageResponse.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        successMessageResponse.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        successMessageResponse.setText(
                String.format(
                        Optional.ofNullable(
                                environment.getProperty(
                                                "format."
                                                + update.getCallbackQuery().getFrom().getLanguageCode()
                                                + ".sub_success"))
                                .orElse("error"),
                        update.getCallbackQuery().getMessage().getText().split("\'")[1]
                )
        );

        return successMessageResponse;
    }
}
