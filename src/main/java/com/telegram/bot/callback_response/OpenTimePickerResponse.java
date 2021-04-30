package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public class OpenTimePickerResponse implements CallbackResponse {

    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {

        EditMessageText messageWithTimePicker = new EditMessageText();

        messageWithTimePicker.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        messageWithTimePicker.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        messageWithTimePicker.setText(
                "\'" + update.getCallbackQuery().getMessage().getText().split("\'")[1] + "\'" +
                "\n\n" + environment.getProperty(
                        "format." + update.getCallbackQuery().getFrom().getLanguageCode() + ".tp_message"));

        messageWithTimePicker.setReplyMarkup(getTimePicker("22:00"));

        return messageWithTimePicker;
    }

}
