package com.telegram.bot.callback_response;

import com.telegram.bot.db.SubscriptionRepository;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TimeDecrementResponse implements CallbackResponse {
    @Override
    public BotApiMethod getMethod(Update update, SubscriptionRepository repository, Environment environment) {
        EditMessageText incrementedTimeResponse = new EditMessageText();

        incrementedTimeResponse.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        incrementedTimeResponse.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        incrementedTimeResponse.setText(update.getCallbackQuery().getMessage().getText());

        String time = getTimePickerTime(update.getCallbackQuery().getMessage().getReplyMarkup());

        incrementedTimeResponse.setReplyMarkup(getTimePicker(decrementTime(time)));

        return incrementedTimeResponse;
    }

    private String decrementTime(String time) {
        String[] splitTime = time.split(":");

        Integer hours = Integer.parseInt(splitTime[0]);
        Integer minutes = Integer.parseInt(splitTime[1]);

        if (minutes != 0) {
            minutes = 0;
        } else {
            minutes = 30;
            if (hours > 0)
                hours--;
            else
                hours = 23;
        }

        return String.format("%02d:%02d", hours, minutes);
    }
}
